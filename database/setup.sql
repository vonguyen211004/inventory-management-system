-- Note: On Railway, you are already connected to the provisioned database.
-- Do not attempt to CREATE DATABASE or USE a different database here.

-- =============================================
-- Create Tables
-- =============================================

-- Products table
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    sku VARCHAR(100),
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    low_stock_threshold INT DEFAULT 10,
    category VARCHAR(100),
    unit VARCHAR(50),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_name (name),
    INDEX idx_sku (sku),
    INDEX idx_category (category),
    INDEX idx_is_active (is_active)
);

-- Customers table
CREATE TABLE IF NOT EXISTS customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    address TEXT,
    city VARCHAR(50),
    customer_type VARCHAR(50),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_phone (phone),
    INDEX idx_name (name),
    INDEX idx_customer_type (customer_type),
    INDEX idx_is_active (is_active)
);

-- Orders table
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(50) UNIQUE NOT NULL,
    customer_id BIGINT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status ENUM('PENDING', 'CONFIRMED', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED') DEFAULT 'PENDING',
    order_date TIMESTAMP NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    INDEX idx_order_number (order_number),
    INDEX idx_customer_id (customer_id),
    INDEX idx_status (status),
    INDEX idx_order_date (order_date)
);

-- Order Items table
CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    INDEX idx_order_id (order_id),
    INDEX idx_product_id (product_id)
);

-- =============================================
-- Insert Sample Data
-- =============================================

-- Insert sample products
INSERT INTO products (name, sku, description, price, quantity, low_stock_threshold, category, unit, is_active) VALUES
('iPhone 15 Pro', 'IPH15PRO', 'Latest iPhone with advanced features', 999.99, 50, 10, 'Electronics', 'piece', TRUE),
('Samsung Galaxy S24', 'SGS24', 'Premium Android smartphone', 899.99, 30, 5, 'Electronics', 'piece', TRUE),
('MacBook Pro 16"', 'MBP16', 'Professional laptop for developers', 2499.99, 20, 3, 'Electronics', 'piece', TRUE),
('Dell XPS 13', 'DXP13', 'Ultrabook for business users', 1299.99, 25, 5, 'Electronics', 'piece', TRUE),
('AirPods Pro', 'APPRO', 'Wireless earbuds with noise cancellation', 249.99, 100, 20, 'Accessories', 'pair', TRUE),
('Magic Mouse', 'MMOUSE', 'Wireless mouse for Mac', 79.99, 75, 15, 'Accessories', 'piece', TRUE),
('USB-C Cable', 'USBC1M', '1 meter USB-C charging cable', 19.99, 200, 50, 'Accessories', 'piece', TRUE),
('Wireless Charger', 'WCHRG', 'Qi-compatible wireless charger', 39.99, 60, 10, 'Accessories', 'piece', TRUE);

-- Insert sample customers
INSERT INTO customers (name, email, phone, address, city, customer_type, is_active) VALUES
('Nguyễn Văn An', 'an.nguyen@email.com', '0123456789', '123 Đường ABC, Quận 1', 'Hồ Chí Minh', 'Individual', TRUE),
('Trần Thị Bình', 'binh.tran@email.com', '0987654321', '456 Đường XYZ, Quận 2', 'Hồ Chí Minh', 'Individual', TRUE),
('Công ty ABC Ltd', 'contact@abc.com', '0245678901', '789 Đường DEF, Quận 3', 'Hồ Chí Minh', 'Corporate', TRUE),
('Lê Văn Cường', 'cuong.le@email.com', '0369258147', '321 Đường GHI, Quận 4', 'Hồ Chí Minh', 'Individual', TRUE),
('Phạm Thị Dung', 'dung.pham@email.com', '0741852963', '654 Đường JKL, Quận 5', 'Hồ Chí Minh', 'Individual', TRUE);

-- =============================================
-- Create Views
-- =============================================

-- Low stock products view
CREATE VIEW low_stock_products AS
SELECT 
    id,
    name,
    sku,
    quantity,
    low_stock_threshold,
    category,
    (quantity <= low_stock_threshold) as is_low_stock
FROM products 
WHERE is_active = TRUE AND quantity <= low_stock_threshold;

-- Revenue summary view
CREATE VIEW revenue_summary AS
SELECT 
    DATE(order_date) as order_date,
    COUNT(*) as total_orders,
    SUM(total_amount) as total_revenue,
    AVG(total_amount) as average_order_value
FROM orders 
WHERE status != 'CANCELLED'
GROUP BY DATE(order_date)
ORDER BY order_date DESC;

-- =============================================
-- Create Stored Procedures
-- =============================================

DELIMITER //

-- Procedure to update product quantity after order
CREATE PROCEDURE UpdateProductQuantity(IN p_product_id BIGINT, IN p_quantity_change INT)
BEGIN
    DECLARE current_quantity INT;
    
    SELECT quantity INTO current_quantity FROM products WHERE id = p_product_id;
    
    IF current_quantity + p_quantity_change >= 0 THEN
        UPDATE products 
        SET quantity = quantity + p_quantity_change,
            updated_at = CURRENT_TIMESTAMP
        WHERE id = p_product_id;
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Insufficient stock quantity';
    END IF;
END //

-- Procedure to get low stock alerts
CREATE PROCEDURE GetLowStockAlerts()
BEGIN
    SELECT 
        p.id,
        p.name,
        p.sku,
        p.quantity,
        p.low_stock_threshold,
        p.category,
        (p.quantity <= p.low_stock_threshold) as is_low_stock
    FROM products p
    WHERE p.is_active = TRUE 
    AND p.quantity <= p.low_stock_threshold
    ORDER BY p.quantity ASC;
END //

DELIMITER ;

-- =============================================
-- Create Indexes for Performance
-- =============================================

-- Additional indexes for better performance
CREATE INDEX idx_products_quantity ON products(quantity);
CREATE INDEX idx_products_price ON products(price);
CREATE INDEX idx_orders_total_amount ON orders(total_amount);
CREATE INDEX idx_order_items_quantity ON order_items(quantity);

-- =============================================
-- Grant Permissions (if needed)
-- =============================================

-- Uncomment and modify as needed for your environment
-- GRANT ALL PRIVILEGES ON inventory_db.* TO 'inventory_user'@'localhost' IDENTIFIED BY 'inventory_password';
-- FLUSH PRIVILEGES;

-- =============================================
-- Database Setup Complete
-- =============================================

SELECT 'Database setup completed successfully!' as status;
