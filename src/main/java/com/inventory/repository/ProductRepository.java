package com.inventory.repository;

import com.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Tìm sản phẩm theo tên (không phân biệt hoa thường)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Tìm sản phẩm theo SKU
    Optional<Product> findBySku(String sku);

    // Tìm sản phẩm theo danh mục
    List<Product> findByCategory(String category);

    // Tìm sản phẩm đang hoạt động
    List<Product> findByIsActiveTrue();

    // Tìm sản phẩm sắp hết hàng
    @Query("SELECT p FROM Product p WHERE p.quantity <= p.lowStockThreshold AND p.isActive = true")
    List<Product> findLowStockProducts();

    // Tìm sản phẩm theo khoảng giá
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    // Đếm số lượng sản phẩm theo danh mục
    Long countByCategory(String category);
}