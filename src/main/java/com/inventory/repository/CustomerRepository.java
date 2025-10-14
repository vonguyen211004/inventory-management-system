package com.inventory.repository;

import com.inventory.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByPhone(String phone);

    List<Customer> findByNameContainingIgnoreCase(String name);

    List<Customer> findByIsActiveTrue();

    List<Customer> findByCustomerType(String customerType);

    List<Customer> findByCity(String city);

    Long countByCustomerType(String customerType);
}