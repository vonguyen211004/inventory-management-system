# 🏪 Inventory Management System

A comprehensive Spring Boot application for managing inventory, customers, and orders with real-time stock alerts and revenue reporting.

## ✨ Features

### 🛍️ Product Management
- Create, read, update, delete products
- Track inventory levels and low stock alerts
- Category-based product organization
- SKU management
- Price and quantity tracking

### 👥 Customer Management
- Customer registration and profile management
- Contact information tracking
- Customer type classification
- Order history tracking

### 📦 Order Management
- Create and manage orders
- Order status tracking (Pending, Confirmed, Processing, Shipped, Delivered, Cancelled)
- Automatic inventory deduction
- Order cancellation with stock restoration

### 📊 Reporting & Analytics
- Revenue reports by date range
- Order statistics and metrics
- Low stock product alerts
- Customer order history

### 🔔 Automated Alerts
- Scheduled low stock notifications
- Daily inventory monitoring
- Configurable alert thresholds

## 🏗️ Architecture

### Technology Stack
- **Backend**: Spring Boot 3.2.0
- **Database**: MySQL 8.0+
- **ORM**: Spring Data JPA / Hibernate
- **Documentation**: Swagger/OpenAPI 3
- **Security**: Spring Security
- **Monitoring**: Spring Boot Actuator
- **Build Tool**: Maven

### Project Structure
```
src/
├── main/
│   ├── java/com/inventory/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST API controllers
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── exception/      # Exception handling
│   │   ├── model/          # JPA entities
│   │   ├── repository/     # Data access layer
│   │   └── service/        # Business logic layer
│   └── resources/
│       ├── application.properties
│       └── application-dev.properties
├── test/                   # Test classes
└── database/
    └── setup.sql          # Database initialization script
```

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd inventory-management-system
   ```

2. **Setup Database**
   ```bash
   mysql -u root -p < database/setup.sql
   ```

3. **Configure Application**
   Update `src/main/resources/application.properties` with your database credentials:
   ```properties
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the Application**
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - Health Check: `http://localhost:8080/actuator/health`

## 📚 API Documentation

### Core Endpoints

#### Products
- `GET /api/products` - Get all products (with pagination)
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product
- `GET /api/products/search?name=xxx` - Search products by name
- `GET /api/products/category/{category}` - Get products by category
- `GET /api/products/low-stock` - Get low stock products

#### Customers
- `GET /api/customers` - Get all customers
- `GET /api/customers/{id}` - Get customer by ID
- `POST /api/customers` - Create new customer
- `PUT /api/customers/{id}` - Update customer
- `DELETE /api/customers/{id}` - Delete customer
- `GET /api/customers/search?name=xxx` - Search customers by name

#### Orders
- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `POST /api/orders` - Create new order
- `PATCH /api/orders/{id}/status?status=xxx` - Update order status
- `DELETE /api/orders/{id}` - Cancel order
- `GET /api/orders/customer/{customerId}` - Get orders by customer

#### Reports
- `GET /api/reports/revenue?startDate=xxx&endDate=xxx` - Get revenue report

### Pagination Support
All list endpoints support pagination:
```
GET /api/products?page=0&size=10&sort=name,asc
```

## 🔧 Configuration

### Database Configuration
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Security Configuration
- All API endpoints are publicly accessible
- CORS enabled for all origins
- CSRF protection disabled for API usage

### Logging Configuration
```properties
logging.level.com.inventory=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

## 📊 Sample Data

The application comes with sample data including:
- 8 sample products across different categories
- 5 sample customers
- Pre-configured low stock thresholds

## 🔔 Automated Features

### Low Stock Alerts
- Runs daily at 9:00 AM
- Checks all products for low stock conditions
- Logs alerts to console (extensible for email/SMS)

### Inventory Management
- Automatic stock deduction on order creation
- Stock restoration on order cancellation
- Real-time inventory tracking

## 🧪 Testing

### Manual Testing
Use the Swagger UI at `http://localhost:8080/swagger-ui.html` to test all endpoints interactively.

### Sample API Calls

#### Create Product
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Product",
    "price": 99.99,
    "quantity": 100,
    "category": "Test"
  }'
```

#### Create Order
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

## 🚀 Deployment

For detailed deployment instructions, see [DEPLOYMENT.md](DEPLOYMENT.md).

### Quick Deployment
1. Build the application: `mvn clean package`
2. Run the JAR: `java -jar target/inventory-management-system-1.0.0.jar`
3. Access at `http://localhost:8080`

## 🔍 Monitoring

### Health Checks
- Application health: `GET /actuator/health`
- Application info: `GET /actuator/info`
- Metrics: `GET /actuator/metrics`

### Logs
- Application logs include detailed business logic tracking
- SQL queries are logged in development mode
- Error handling with detailed stack traces

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

For support and questions:
- Check the [API Documentation](http://localhost:8080/swagger-ui.html)
- Review the [Deployment Guide](DEPLOYMENT.md)
- Check application logs for error details

## 🎯 Roadmap

- [ ] User authentication and authorization
- [ ] Email/SMS notifications for low stock
- [ ] Advanced reporting and analytics
- [ ] Mobile app integration
- [ ] Multi-warehouse support
- [ ] Barcode scanning support
- [ ] Automated reorder suggestions

---

**Built with ❤️ using Spring Boot**
