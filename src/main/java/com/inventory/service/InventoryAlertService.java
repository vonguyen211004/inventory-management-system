package com.inventory.service;

import com.inventory.model.Product;
import com.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryAlertService {

    private ProductRepository productRepository;

    @Autowired
    public InventoryAlertService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Chạy mỗi ngày lúc 9:00 sáng để kiểm tra hàng tồn kho
    @Scheduled(cron = "0 0 9 * * *")
    public void checkLowStockProducts() {
        List<Product> lowStockProducts = productRepository.findLowStockProducts();

        if (!lowStockProducts.isEmpty()) {
            System.out.println("CẢNH BÁO: Có " + lowStockProducts.size() + " sản phẩm sắp hết hàng!");
            for (Product product : lowStockProducts) {
                System.out.println("Sản phẩm: " + product.getName()
                        + " (ID: " + product.getId() + ") - Số lượng còn lại: "
                        + product.getQuantity() + " (Ngưỡng: " + product.getLowStockThreshold() + ")");
                sendLowStockAlert(product);
            }
        } else {
            System.out.println("Tất cả sản phẩm đều có đủ hàng trong kho");
        }
    }

    private void sendLowStockAlert(Product product) {
        System.out.println("Sending alert for product: " + product.getName());
        // TODO: implement email/SMS notification
    }

    public List<Product> getLowStockProductsNow() {
        return productRepository.findLowStockProducts();
    }
}
