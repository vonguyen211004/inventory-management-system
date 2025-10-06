package com.inventory.repository;

import com.inventory.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Tìm đơn hàng theo số đơn hàng
    Optional<Order> findByOrderNumber(String orderNumber);

    // Tìm đơn hàng theo khách hàng
    List<Order> findByCustomerId(Long customerId);

    // Tìm đơn hàng theo trạng thái
    List<Order> findByStatus(Order.OrderStatus status);

    // Tìm đơn hàng theo khoảng thời gian
    @Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    List<Order> findByOrderDateBetween(@Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate);

    // Tính tổng doanh thu theo khoảng thời gian
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate AND o.status != 'CANCELLED'")
    BigDecimal calculateRevenueBetweenDates(@Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);

    // Đếm số đơn hàng theo trạng thái
    Long countByStatus(Order.OrderStatus status);

    // Tìm top khách hàng theo tổng giá trị đơn hàng
    @Query("SELECT o.customer.id, o.customer.name, SUM(o.totalAmount) as total " +
            "FROM Order o WHERE o.status != 'CANCELLED' " +
            "GROUP BY o.customer.id, o.customer.name " +
            "ORDER BY total DESC")
    List<Object[]> findTopCustomersByRevenue();
}