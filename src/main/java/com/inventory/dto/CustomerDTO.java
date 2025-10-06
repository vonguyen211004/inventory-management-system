package com.inventory.dto;

import jakarta.validation.constraints.*;

public class CustomerDTO {

    private Long id;

    @NotBlank(message = "Tên khách hàng không được để trống")
    private String name;

    @Email(message = "Email không hợp lệ")
    private String email;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    private String address;

    private String city;

    private String customerType;

    private Boolean isActive;

    private Integer totalOrders;

    // ===== Constructor =====
    public CustomerDTO() {}

    public CustomerDTO(Long id, String name, String email, String phone, String address,
                       String city, String customerType, Boolean isActive, Integer totalOrders) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.customerType = customerType;
        this.isActive = isActive;
        this.totalOrders = totalOrders;
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

    public Integer getTotalOrders() { return totalOrders; }
    public void setTotalOrders(Integer totalOrders) { this.totalOrders = totalOrders; }

    // ===== Builder manually =====
    public static CustomerDTOBuilder builder() { return new CustomerDTOBuilder(); }

    public static class CustomerDTOBuilder {
        private Long id;
        private String name;
        private String email;
        private String phone;
        private String address;
        private String city;
        private String customerType;
        private Boolean isActive;
        private Integer totalOrders;

        public CustomerDTOBuilder id(Long id) { this.id = id; return this; }
        public CustomerDTOBuilder name(String name) { this.name = name; return this; }
        public CustomerDTOBuilder email(String email) { this.email = email; return this; }
        public CustomerDTOBuilder phone(String phone) { this.phone = phone; return this; }
        public CustomerDTOBuilder address(String address) { this.address = address; return this; }
        public CustomerDTOBuilder city(String city) { this.city = city; return this; }
        public CustomerDTOBuilder customerType(String customerType) { this.customerType = customerType; return this; }
        public CustomerDTOBuilder isActive(Boolean isActive) { this.isActive = isActive; return this; }
        public CustomerDTOBuilder totalOrders(Integer totalOrders) { this.totalOrders = totalOrders; return this; }

        public CustomerDTO build() {
            return new CustomerDTO(id, name, email, phone, address, city, customerType, isActive, totalOrders);
        }
    }
}
