package com.inventory.service;

import com.inventory.dto.OrderDTO;
import com.inventory.dto.RevenueReportDTO;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.*;
import com.inventory.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private OrderItemRepository orderItemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        ProductRepository productRepository,
                        OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    // Lấy tất cả đơn hàng
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy đơn hàng theo ID
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng với ID: " + id));
        return convertToDTO(order);
    }

    // Tạo đơn hàng mới
    @Transactional
    public OrderDTO createOrder(OrderDTO dto) {
        log.info("Creating new order for customer ID: {}", dto.getCustomerId());
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khách hàng với ID: " + dto.getCustomerId()));

        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.PENDING);
        order.setNotes(dto.getNotes());
        order.setOrderItems(new ArrayList<>());

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderDTO.OrderItemDTO itemDTO : dto.getOrderItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID: " + itemDTO.getProductId()));

            if (product.getQuantity() < itemDTO.getQuantity()) {
                throw new IllegalStateException("Sản phẩm " + product.getName() + " không đủ số lượng trong kho");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.calculateSubtotal();
            order.addOrderItem(orderItem);

            totalAmount = totalAmount.add(orderItem.getSubtotal());

            // giảm số lượng tồn kho
            product.setQuantity(product.getQuantity() - itemDTO.getQuantity());
            productRepository.save(product);
        }

        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);
        log.info("Order created successfully with ID: {} and total amount: {}", savedOrder.getId(), totalAmount);
        return convertToDTO(savedOrder);
    }

    // Cập nhật trạng thái đơn hàng
    @Transactional
    public OrderDTO updateOrderStatus(Long id, Order.OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng với ID: " + id));
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return convertToDTO(updatedOrder);
    }

    // Hủy đơn hàng
    @Transactional
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng với ID: " + id));

        if (order.getStatus() == Order.OrderStatus.DELIVERED) {
            throw new IllegalStateException("Không thể hủy đơn hàng đã giao");
        }

        // Hoàn lại số lượng sản phẩm vào kho
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() + item.getQuantity());
            productRepository.save(product);
        }

        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    // Lấy đơn hàng theo khách hàng
    public List<OrderDTO> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Tính doanh thu theo khoảng thời gian
    public RevenueReportDTO getRevenueReport(LocalDateTime startDate, LocalDateTime endDate) {
        List<Order> orders = orderRepository.findByOrderDateBetween(startDate, endDate);

        BigDecimal totalRevenue = orderRepository.calculateRevenueBetweenDates(startDate, endDate);
        if (totalRevenue == null) {
            totalRevenue = BigDecimal.ZERO;
        }

        long totalOrders = orders.size();
        long completedOrders = orders.stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.DELIVERED)
                .count();
        long cancelledOrders = orders.stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.CANCELLED)
                .count();

        BigDecimal averageOrderValue = totalOrders > 0
                ? totalRevenue.divide(BigDecimal.valueOf(totalOrders), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        RevenueReportDTO report = new RevenueReportDTO();
        report.setStartDate(startDate);
        report.setEndDate(endDate);
        report.setTotalRevenue(totalRevenue);
        report.setTotalOrders(totalOrders);
        report.setCompletedOrders(completedOrders);
        report.setCancelledOrders(cancelledOrders);
        report.setAverageOrderValue(averageOrderValue);
        return report;
    }

    // Generate order number
    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Convert Entity to DTO
    private OrderDTO convertToDTO(Order order) {
        List<OrderDTO.OrderItemDTO> itemDTOs = new ArrayList<>();
        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
                OrderDTO.OrderItemDTO itemDTO = new OrderDTO.OrderItemDTO();
                itemDTO.setProductId(item.getProduct().getId());
                itemDTO.setProductName(item.getProduct().getName());
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setUnitPrice(item.getUnitPrice());
                itemDTO.setSubtotal(item.getSubtotal());
                itemDTOs.add(itemDTO);
            }
        }

        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setCustomerName(order.getCustomer().getName());
        dto.setOrderItems(itemDTOs);
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setOrderDate(order.getOrderDate());
        dto.setNotes(order.getNotes());
        return dto;
    }
}
