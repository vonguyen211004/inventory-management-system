# 🏪 Hệ thống Quản lý Kho hàng nguyenVo (nguyenVo Inventory Management System)

Một ứng dụng Spring Boot toàn diện để quản lý hàng tồn kho, khách hàng và đơn hàng, với các tính năng cảnh báo tồn kho theo thời gian thực và báo cáo doanh thu.

## ✨ Tính năng

### 🛍️ Quản lý sản phẩm
- Thêm, xem, sửa, xóa sản phẩm
- Theo dõi mức tồn kho và cảnh báo khi sắp hết hàng
- Phân loại sản phẩm theo danh mục
- Quản lý mã SKU
- Theo dõi giá và số lượng

### 👥 Quản lý khách hàng
- Đăng ký và quản lý hồ sơ khách hàng
- Theo dõi thông tin liên hệ
- Phân loại loại khách hàng
- Theo dõi lịch sử đặt hàng

### 📦 Quản lý đơn hàng
- Tạo và quản lý đơn hàng
- Theo dõi trạng thái đơn hàng (Chờ xử lý, Đã xác nhận, Đang xử lý, Đã gửi, Đã giao, Đã hủy)
- Tự động trừ hàng tồn kho
- Hủy đơn hàng kèm khôi phục lại số lượng hàng trong kho

### 📊 Báo cáo & Phân tích
- Báo cáo doanh thu theo khoảng thời gian
- Thống kê và chỉ số đơn hàng
- Cảnh báo sản phẩm sắp hết hàng
- Lịch sử đơn hàng của khách hàng

### 🔔 Cảnh báo tự động
- Thông báo định kỳ khi hàng tồn thấp
- Theo dõi hàng tồn kho hàng ngày
- Cấu hình ngưỡng cảnh báo linh hoạt

## 🏗️ Kiến trúc hệ thống

### Công nghệ sử dụng
- **Backend**: Spring Boot 3.2.0
- **Database**: MySQL 8.0+
- **ORM**: Spring Data JPA / Hibernate
- **Documentation**: Swagger/OpenAPI 3
- **Security**: Spring Security
- **Monitoring**: Spring Boot Actuator
- **Build Tool**: Maven

### Cấu trúc dự án
```
src/
├── main/
│   ├── java/com/inventory/
│   │   ├── config/          # Các lớp cấu hình
│   │   ├── controller/      # Bộ điều khiển REST API
│   │   ├── dto/            # Đối tượng truyền dữ liệu (DTO)
│   │   ├── exception/      # Exception handling
│   │   ├── model/          # JPA entities
│   │   ├── repository/     # Data access layer
│   │   └── service/        # Business logic layer
│   └── resources/
│       ├── application.properties
│       └── application-dev.properties
├── test/                   # Test classes
└── database/
    └── setup.sql          # Tập lệnh khởi tạo cơ sở dữ liệu
```

## 🚀 Bắt đầu nhanh

### Yêu cầu
- Java 17+
- Maven 3.6+
- MySQL 8.0+

### Cài đặt

1. **Clone dự án**
   ```bash
   git clone <repository-url>
   cd inventory-management-system
   ```

2. **Thiết lập cơ sở dữ liệu**
   ```bash
   mysql -u root -p < database/setup.sql
   ```

3. **Cấu hình ứng dụng**
   Cập nhật file src/main/resources/application.properties với thông tin của bạn:
   ```properties
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Chạy ứng dụng**
   ```bash
   mvn spring-boot:run
   ```

5. **Truy cập ứng dụng**
   - API gốc: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - Health Check: `http://localhost:8080/actuator/health`

## 📚 API Documentation

### Các Endpoint chính

#### Sản phẩm
- `GET /api/products` - Lấy danh sách sản phẩm (hỗ trợ phân trang)
- `GET /api/products/{id}` - Lấy sản phẩm theo ID
- `POST /api/products` - Tạo sản phẩm mới
- `PUT /api/products/{id}` - Cập nhật sản phẩm
- `DELETE /api/products/{id}` - Xóa sản phẩm
- `GET /api/products/search?name=xxx` - Tìm sản phẩm theo tên
- `GET /api/products/category/{category}` - GLấy sản phẩm theo danh mục
- `GET /api/products/low-stock` - Lấy danh sách sản phẩm sắp hết hàng

#### Khách hàng
- `GET /api/customers` - Lấy danh sách khách hàng
- `GET /api/customers/{id}` - Lấy khách hàng theo ID
- `POST /api/customers` - Tạo khách hàng mới
- `PUT /api/customers/{id}` - Cập nhật khách hàng
- `DELETE /api/customers/{id}` - Xóa khách hàng
- `GET /api/customers/search?name=xxx` - Tìm khách hàng theo tên

#### Đơn hàng
- `GET /api/orders` - Lấy danh sách đơn hàng
- `GET /api/orders/{id}` - Lấy đơn hàng theo ID
- `POST /api/orders` - Tạo đơn hàng mới
- `PATCH /api/orders/{id}/status?status=xxx` - Cập nhật trạng thái đơn hàng
- `DELETE /api/orders/{id}` - Hủy đơn hàng
- `GET /api/orders/customer/{customerId}` - Lấy đơn hàng của khách hàng

#### Báo cáo
- `GET /api/reports/revenue?startDate=xxx&endDate=xxx` - Lấy báo cáo doanh thu

### Phân trang
Tất cả các endpoint danh sách đều hỗ trợ phân trang:
```
GET /api/products?page=0&size=10&sort=name,asc
```

## 🔧 Cấu hình

### Cơ sở dữ liệu
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Bảo mật
- Tất cả API đều công khai
- Cho phép CORS cho mọi nguồn
- Tắt CSRF cho API

### Logging Configuration
```properties
logging.level.com.inventory=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

## 🧪 Kiểm thử

### Kiểm thử thủ công
Dùng Swagger UI tại http://localhost:8080/swagger-ui.html để thử các API tương tác.

### Ví dụ gọi API

#### Tạo sản phẩm
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test san pham",
    "price": 150,
    "quantity": 100,
    "category": "Test"
  }'
```

#### Tạo đơn hàng
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "orderItems": [
      {
        "productId": 1,
        "quantity": 2
      }
    ]
  }'
```
