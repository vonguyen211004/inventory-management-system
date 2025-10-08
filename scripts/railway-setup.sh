#!/bin/bash

# Railway Database Setup Script
# This script helps initialize the database on Railway

echo "🚀 Setting up Inventory Management System Database on Railway..."

# Check if Railway CLI is installed
if ! command -v railway &> /dev/null; then
    echo "❌ Railway CLI not found. Please install it first:"
    echo "npm install -g @railway/cli"
    exit 1
fi

# Check if user is logged in
if ! railway whoami &> /dev/null; then
    echo "🔐 Please login to Railway first:"
    echo "railway login"
    exit 1
fi

echo "✅ Railway CLI is ready"

# Connect to MySQL database
echo "🔗 Connecting to Railway MySQL database..."
railway connect mysql

# Check if database connection is successful
if [ $? -eq 0 ]; then
    echo "✅ Successfully connected to Railway MySQL"
else
    echo "❌ Failed to connect to Railway MySQL"
    exit 1
fi

# Run database setup script
echo "📊 Initializing database schema..."
if [ -f "database/setup.sql" ]; then
    mysql -h $MYSQL_HOST -P $MYSQL_PORT -u $MYSQL_USER -p$MYSQL_PASSWORD $MYSQL_DATABASE < database/setup.sql
    if [ $? -eq 0 ]; then
        echo "✅ Database schema initialized successfully"
    else
        echo "❌ Failed to initialize database schema"
        exit 1
    fi
else
    echo "❌ Database setup script not found at database/setup.sql"
    exit 1
fi

echo "🎉 Database setup completed successfully!"
echo ""
echo "Next steps:"
echo "1. Deploy your application to Railway"
echo "2. Check the application logs for any errors"
echo "3. Test the API endpoints"
echo "4. Access Swagger UI at: https://your-app.railway.app/swagger-ui.html"
