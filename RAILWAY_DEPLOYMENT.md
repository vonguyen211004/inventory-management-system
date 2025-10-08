# 🚀 Hướng dẫn Deploy Inventory Management System lên Railway

Hướng dẫn chi tiết để deploy ứng dụng Spring Boot lên Railway platform.

## 📋 Yêu cầu trước khi bắt đầu

### 1. Tài khoản và công cụ cần thiết
- [x] Tài khoản GitHub (để lưu trữ code)
- [x] Tài khoản Railway (đăng ký tại [railway.app](https://railway.app))
- [x] Git đã cài đặt trên máy local
- [x] Java 17+ và Maven 3.6+ (để test local)

### 2. Chuẩn bị project
- [x] Project đã được build thành công với `mvn clean package`
- [x] Database schema đã được thiết kế
- [x] Application properties đã được cấu hình

## 🏗️ Cấu trúc Project cho Railway

### File cấu hình cần thiết:
```
inventory-management-system/
├── pom.xml                          # Maven configuration
├── railway.json                     # Railway deployment config
├── Procfile                         # Process definition
├── system.properties               # Java version
├── src/main/resources/
│   ├── application.properties       # Main config
│   └── application-railway.properties # Railway-specific config
└── database/
    └── setup.sql                   # Database initialization
```

## 🚀 Bước 1: Chuẩn bị Repository

### 1.1. Khởi tạo Git repository (nếu chưa có)
```bash
cd inventory-management-system
git init
git add .
git commit -m "Initial commit: Inventory Management System"
```

### 1.2. Push lên GitHub
```bash
# Tạo repository trên GitHub, sau đó:
git remote add origin https://github.com/yourusername/inventory-management-system.git
git branch -M main
git push -u origin main
```

## 🚀 Bước 2: Cấu hình Railway

### 2.1. Đăng nhập Railway
1. Truy cập [railway.app](https://railway.app)
2. Đăng nhập bằng GitHub account
3. Authorize Railway để truy cập GitHub repositories

### 2.2. Tạo New Project
1. Click **"New Project"**
2. Chọn **"Deploy from GitHub repo"**
3. Chọn repository `inventory-management-system`
4. Railway sẽ tự động detect Spring Boot project

## 🚀 Bước 3: Cấu hình Database

### 3.1. Thêm MySQL Database
1. Trong Railway dashboard, click **"+ New"**
2. Chọn **"Database"** → **"MySQL"**
3. Railway sẽ tạo MySQL instance và cung cấp connection string

### 3.2. Lấy thông tin kết nối Database
Railway sẽ cung cấp các biến môi trường:
- `MYSQL_URL` - Connection URL
- `MYSQL_HOST` - Database host
- `MYSQL_PORT` - Database port (thường là 3306)
- `MYSQL_DATABASE` - Database name
- `MYSQL_USER` - Username
- `MYSQL_PASSWORD` - Password

## 🚀 Bước 4: Cấu hình Environment Variables

### 4.1. Trong Railway Dashboard
1. Vào **"Variables"** tab
2. Thêm các biến môi trường sau:

```bash
# Database Configuration
DB_USERNAME=${MYSQL_USER}
DB_PASSWORD=${MYSQL_PASSWORD}
DB_URL=${MYSQL_URL}

# Application Configuration
SPRING_PROFILES_ACTIVE=railway
JAVA_OPTS=-Xmx512m -Xms256m

# Server Configuration
PORT=8080
```

### 4.2. Các biến môi trường quan trọng khác:
```bash
# Logging
LOGGING_LEVEL_COM_INVENTORY=INFO
LOGGING_LEVEL_ORG_HIBERNATE_SQL=WARN

# JPA Configuration
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=false

# Security (nếu cần)
SPRING_SECURITY_USER_NAME=admin
SPRING_SECURITY_USER_PASSWORD=your_secure_password
```

## 🚀 Bước 5: Cấu hình Build và Deploy

### 5.1. Railway sẽ tự động detect:
- **Build Command**: `mvn clean package -DskipTests`
- **Start Command**: `java -jar target/inventory-management-system-1.0.0.jar`
- **Port**: 8080 (hoặc PORT environment variable)

### 5.2. Custom Build Configuration (nếu cần)
Trong Railway dashboard:
1. Vào **"Settings"** → **"Build"**
2. Cấu hình:
   - **Build Command**: `mvn clean package -DskipTests`
   - **Start Command**: `java -jar target/inventory-management-system-1.0.0.jar`

## 🚀 Bước 6: Database Initialization

### 6.1. Tạo Database Schema
Railway MySQL không tự động chạy setup.sql, bạn cần:

1. **Option 1: Sử dụng Railway CLI**
```bash
# Cài đặt Railway CLI
npm install -g @railway/cli

# Login
railway login

# Connect to database
railway connect mysql

# Chạy setup script
mysql -u $MYSQL_USER -p$MYSQL_PASSWORD $MYSQL_DATABASE < database/setup.sql
```

2. **Option 2: Sử dụng MySQL Workbench hoặc phpMyAdmin**
- Kết nối đến Railway MySQL database
- Import file `database/setup.sql`

3. **Option 3: Sử dụng Application Startup**
- Cấu hình Spring Boot để tự động tạo schema
- Set `spring.jpa.hibernate.ddl-auto=create-drop` (chỉ cho lần đầu)

## 🚀 Bước 7: Deploy và Test

### 7.1. Trigger Deployment
1. Railway sẽ tự động deploy khi có commit mới
2. Hoặc manual deploy từ dashboard
3. Theo dõi logs trong **"Deployments"** tab

### 7.2. Kiểm tra Deployment
1. **Health Check**: `https://your-app.railway.app/actuator/health`
2. **API Documentation**: `https://your-app.railway.app/swagger-ui.html`
3. **Database Connection**: Kiểm tra logs để đảm bảo kết nối DB thành công

## 🔧 Cấu hình Nâng cao

### Custom Domain (Optional)
1. Vào **"Settings"** → **"Domains"**
2. Thêm custom domain
3. Cấu hình DNS records

### Monitoring và Logs
1. **Logs**: Xem real-time logs trong Railway dashboard
2. **Metrics**: Sử dụng Spring Boot Actuator endpoints
3. **Alerts**: Cấu hình email notifications

### Scaling
1. **Horizontal Scaling**: Railway tự động scale
2. **Resource Limits**: Cấu hình trong **"Settings"** → **"Resources"**

## 🐛 Troubleshooting

### Lỗi thường gặp:

#### 1. Database Connection Failed
```bash
# Kiểm tra environment variables
echo $MYSQL_URL
echo $DB_USERNAME
echo $DB_PASSWORD

# Kiểm tra logs
railway logs
```

#### 2. Build Failed
```bash
# Kiểm tra Java version
java -version

# Test build local
mvn clean package -DskipTests
```

#### 3. Application Won't Start
```bash
# Kiểm tra port configuration
echo $PORT

# Kiểm tra memory limits
echo $JAVA_OPTS
```

### Debug Commands:
```bash
# Xem logs
railway logs

# Connect to database
railway connect mysql

# SSH vào container
railway shell
```

## 📊 Monitoring và Maintenance

### Health Checks
- **Application**: `GET /actuator/health`
- **Database**: Kiểm tra connection trong logs
- **Memory**: `GET /actuator/metrics/jvm.memory.used`

### Backup Strategy
1. **Database Backup**: Sử dụng Railway's backup features
2. **Code Backup**: GitHub repository
3. **Configuration Backup**: Export environment variables

### Performance Optimization
1. **Connection Pooling**: Cấu hình HikariCP
2. **Caching**: Thêm Redis nếu cần
3. **Logging**: Giảm log level trong production

## 🚀 Production Checklist

- [ ] Database schema đã được tạo
- [ ] Environment variables đã được cấu hình
- [ ] Application health check thành công
- [ ] API endpoints hoạt động bình thường
- [ ] Swagger UI accessible
- [ ] Database connection stable
- [ ] Logs không có error
- [ ] Custom domain (nếu cần) đã được cấu hình
- [ ] SSL certificate active
- [ ] Monitoring và alerting đã được setup

## 📞 Support

### Railway Support
- **Documentation**: [docs.railway.app](https://docs.railway.app)
- **Community**: [Railway Discord](https://discord.gg/railway)
- **GitHub Issues**: [github.com/railwayapp/cli](https://github.com/railwayapp/cli)

### Application Support
- **Logs**: Railway dashboard → Deployments → View Logs
- **Database**: Railway dashboard → Database → Connect
- **Metrics**: Application → `/actuator/metrics`

---

## 🎯 Kết quả mong đợi

Sau khi deploy thành công, bạn sẽ có:
- ✅ Ứng dụng Spring Boot chạy trên Railway
- ✅ MySQL database được cấu hình và kết nối
- ✅ API endpoints accessible qua HTTPS
- ✅ Swagger UI để test API
- ✅ Health check endpoints
- ✅ Automatic scaling và monitoring

**URL mẫu**: `https://inventory-management-system-production.railway.app`

---

**Chúc bạn deploy thành công! 🚀**
