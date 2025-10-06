package com.inventory.repository;

import com.inventory.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Tìm khách hàng theo email
    Optional<Customer> findByEmail(String email);

    // Tìm khách hàng theo số điện thoại
    Optional<Customer> findByPhone(String phone);

    // Tìm khách hàng theo tên
    List<Customer> findByNameContainingIgnoreCase(String name);

    // Tìm khách hàng đang hoạt động
    List<Customer> findByIsActiveTrue();

    // Tìm khách hàng theo loại
    List<Customer> findByCustomerType(String customerType);

    // Tìm khách hàng theo thành phố
    List<Customer> findByCity(String city);

    // Đếm số lượng khách hàng theo loại
    Long countByCustomerType(String customerType);
}