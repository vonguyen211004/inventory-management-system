@echo off
REM Railway Database Setup Script for Windows
REM This script helps initialize the database on Railway

echo 🚀 Setting up Inventory Management System Database on Railway...

REM Check if Railway CLI is installed
railway --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Railway CLI not found. Please install it first:
    echo npm install -g @railway/cli
    pause
    exit /b 1
)

REM Check if user is logged in
railway whoami >nul 2>&1
if %errorlevel% neq 0 (
    echo 🔐 Please login to Railway first:
    echo railway login
    pause
    exit /b 1
)

echo ✅ Railway CLI is ready

REM Connect to MySQL database
echo 🔗 Connecting to Railway MySQL database...
railway connect mysql

REM Check if database connection is successful
if %errorlevel% neq 0 (
    echo ❌ Failed to connect to Railway MySQL
    pause
    exit /b 1
)

echo ✅ Successfully connected to Railway MySQL

REM Run database setup script
echo 📊 Initializing database schema...
if exist "database\setup.sql" (
    mysql -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% %MYSQL_DATABASE% < database\setup.sql
    if %errorlevel% equ 0 (
        echo ✅ Database schema initialized successfully
    ) else (
        echo ❌ Failed to initialize database schema
        pause
        exit /b 1
    )
) else (
    echo ❌ Database setup script not found at database\setup.sql
    pause
    exit /b 1
)

echo 🎉 Database setup completed successfully!
echo.
echo Next steps:
echo 1. Deploy your application to Railway
echo 2. Check the application logs for any errors
echo 3. Test the API endpoints
echo 4. Access Swagger UI at: https://your-app.railway.app/swagger-ui.html
pause
