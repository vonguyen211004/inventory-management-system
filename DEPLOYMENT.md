# 🚀 Inventory Management System - Deployment Guide

## 📋 Prerequisites

### Required Software:
- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **Git** (for cloning repository)

### System Requirements:
- **RAM**: Minimum 2GB, Recommended 4GB+
- **Disk Space**: At least 1GB free space
- **OS**: Windows 10+, macOS 10.14+, or Linux

## 🛠️ Installation Steps

### Step 1: Clone Repository
```bash
git clone <repository-url>
cd inventory-management-system
```

### Step 2: Database Setup

#### Option A: Using MySQL Command Line
```bash
# Login to MySQL
mysql -u root -p

# Run the setup script
source database/setup.sql
```

#### Option B: Using MySQL Workbench
1. Open MySQL Workbench
2. Connect to your MySQL server
3. Open `database/setup.sql`
4. Execute the script

#### Option C: Manual Database Creation
```sql
CREATE DATABASE inventory_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Step 3: Configure Application

#### Update Database Configuration
Edit `src/main/resources/application.properties`:

```properties
# Update these values according to your MySQL setup
spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Step 4: Build and Run

#### Development Mode
```bash
# Clean and compile
mvn clean compile

# Run the application
mvn spring-boot:run
```

#### Production Mode
```bash
# Build JAR file
mvn clean package -DskipTests

# Run JAR file
java -jar target/inventory-management-system-1.0.0.jar
```

## 🌐 Access Points

After successful deployment, you can access:

### API Endpoints:
- **Base URL**: `http://localhost:8080`
- **API Documentation**: `http://localhost:8080/swagger-ui.html`
- **Health Check**: `http://localhost:8080/actuator/health`

### Main API Endpoints:
- **Products**: `http://localhost:8080/api/products`
- **Customers**: `http://localhost:8080/api/customers`
- **Orders**: `http://localhost:8080/api/orders`
- **Reports**: `http://localhost:8080/api/reports`

## 🔧 Configuration Options

### Environment Variables
You can override configuration using environment variables:

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/inventory_db
export SPRING_DATASOURCE_USERNAME=your_username
export SPRING_DATASOURCE_PASSWORD=your_password
export SERVER_PORT=8080
```

### Application Profiles

#### Development Profile
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

#### Production Profile
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## 🐳 Docker Deployment (Optional)

### Create Dockerfile
```dockerfile
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/inventory-management-system-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Build and Run with Docker
```bash
# Build Docker image
docker build -t inventory-management-system .

# Run container
docker run -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/inventory_db inventory-management-system
```

## 📊 Monitoring and Health Checks

### Actuator Endpoints
- **Health**: `GET /actuator/health`
- **Info**: `GET /actuator/info`
- **Metrics**: `GET /actuator/metrics`

### Log Files
- **Location**: `logs/` directory (if configured)
- **Level**: DEBUG for development, INFO for production

## 🔒 Security Configuration

### Default Security
- All API endpoints are publicly accessible
- CORS enabled for all origins
- CSRF protection disabled for API usage

### Production Security Recommendations
1. Enable authentication and authorization
2. Configure HTTPS
3. Set up proper CORS policies
4. Use environment variables for sensitive data

## 🚨 Troubleshooting

### Common Issues:

#### 1. Database Connection Error
```
Error: Could not create connection to database server
```
**Solution**: Check MySQL service is running and credentials are correct

#### 2. Port Already in Use
```
Error: Port 8080 is already in use
```
**Solution**: Change port in `application.properties` or kill process using port 8080

#### 3. Memory Issues
```
Error: OutOfMemoryError
```
**Solution**: Increase JVM heap size:
```bash
java -Xmx2g -jar target/inventory-management-system-1.0.0.jar
```

#### 4. Database Schema Issues
```
Error: Table doesn't exist
```
**Solution**: Run the database setup script or set `spring.jpa.hibernate.ddl-auto=create`

### Log Analysis
Check application logs for detailed error information:
```bash
tail -f logs/application.log
```

## 📈 Performance Optimization

### Database Optimization
1. Create appropriate indexes
2. Use connection pooling
3. Optimize queries

### Application Optimization
1. Enable caching
2. Use pagination for large datasets
3. Configure JVM parameters

### Example JVM Configuration
```bash
java -Xms512m -Xmx2g -XX:+UseG1GC -jar target/inventory-management-system-1.0.0.jar
```

## 🔄 Backup and Recovery

### Database Backup
```bash
mysqldump -u username -p inventory_db > backup.sql
```

### Database Restore
```bash
mysql -u username -p inventory_db < backup.sql
```

## 📞 Support

### API Testing
Use the Swagger UI at `http://localhost:8080/swagger-ui.html` to test all endpoints.

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

#### Get All Products (with pagination)
```bash
curl "http://localhost:8080/api/products?page=0&size=10&sort=name,asc"
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
    ],
    "notes": "Test order"
  }'
```

## ✅ Deployment Checklist

- [ ] Java 17+ installed
- [ ] Maven 3.6+ installed
- [ ] MySQL 8.0+ running
- [ ] Database created and configured
- [ ] Application properties updated
- [ ] Application builds successfully
- [ ] Application starts without errors
- [ ] API endpoints accessible
- [ ] Swagger UI working
- [ ] Health check passing

## 🎉 Success!

If all steps are completed successfully, your Inventory Management System should be running at `http://localhost:8080` with full API documentation available at `http://localhost:8080/swagger-ui.html`.
