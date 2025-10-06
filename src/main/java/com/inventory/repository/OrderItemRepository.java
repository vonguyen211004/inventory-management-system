package com.inventory.repository;

import com.inventory.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Tìm OrderItem theo Order ID
    List<OrderItem> findByOrderId(Long orderId);

    // Tìm OrderItem theo Product ID
    List<OrderItem> findByProductId(Long productId);

    // Tìm sản phẩm bán chạy nhất
    @Query("SELECT oi.product.id, oi.product.name, SUM(oi.quantity) as totalSold " +
            "FROM OrderItem oi " +
            "WHERE oi.order.orderDate BETWEEN :startDate AND :endDate " +
            "GROUP BY oi.product.id, oi.product.name " +
            "ORDER BY totalSold DESC")
    List<Object[]> findBestSellingProducts(@Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);
}