package com.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên khách hàng không được để trống")
    @Column(nullable = false, length = 200)
    private String name;

    @Email(message = "Email không hợp lệ")
    @Column(unique = true, length = 100)
    private String email;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "Số điện thoại không hợp lệ")
    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(length = 50)
    private String city;

    @Column(name = "customer_type", length = 50)
    private String customerType;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ===== Constructor =====
    public Customer() {}

    public Customer(Long id, String name, String email, String phone, String address,
                    String city, String customerType, Boolean isActive, List<Order> orders,
                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.customerType = customerType;
        this.isActive = isActive;
        this.orders = orders;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ===== Getter & Setter =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCustomerType() { return customerType; }
    public void setCustomerType(String customerType) { this.customerType = customerType; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // ===== Builder manually =====
    public static CustomerBuilder builder() { return new CustomerBuilder(); }

    public static class CustomerBuilder {
        private Long id;
        private String name;
        private String email;
        private String phone;
        private String address;
        private String city;
        private String customerType;
        private Boolean isActive;
        private List<Order> orders = new ArrayList<>();
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public CustomerBuilder id(Long id) { this.id = id; return this; }
        public CustomerBuilder name(String name) { this.name = name; return this; }
        public CustomerBuilder email(String email) { this.email = email; return this; }
        public CustomerBuilder phone(String phone) { this.phone = phone; return this; }
        public CustomerBuilder address(String address) { this.address = address; return this;}
        public CustomerBuilder city(String city) { this.city = city; return this; }
        public CustomerBuilder customerType(String customerType) { this.customerType = customerType; return this; }
        public CustomerBuilder isActive(Boolean isActive) { this.isActive = isActive; return this; }
        public CustomerBuilder orders(List<Order> orders) { this.orders = orders; return this; }
        public CustomerBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public CustomerBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Customer build() {
            return new Customer(id, name, email, phone, address, city, customerType,
                    isActive, orders, createdAt, updatedAt);
        }
    }
}
