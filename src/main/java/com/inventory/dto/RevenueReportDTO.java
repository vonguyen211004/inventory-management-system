package com.inventory.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RevenueReportDTO {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal totalRevenue;
    private Long totalOrders;
    private Long completedOrders;
    private Long cancelledOrders;
    private BigDecimal averageOrderValue;

    public RevenueReportDTO() {}

    public RevenueReportDTO(LocalDateTime startDate, LocalDateTime endDate, BigDecimal totalRevenue,
                            Long totalOrders, Long completedOrders, Long cancelledOrders, BigDecimal averageOrderValue) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalRevenue = totalRevenue;
        this.totalOrders = totalOrders;
        this.completedOrders = completedOrders;
        this.cancelledOrders = cancelledOrders;
        this.averageOrderValue = averageOrderValue;
    }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }

    public Long getTotalOrders() { return totalOrders; }
    public void setTotalOrders(Long totalOrders) { this.totalOrders = totalOrders; }

    public Long getCompletedOrders() { return completedOrders; }
    public void setCompletedOrders(Long completedOrders) { this.completedOrders = completedOrders; }

    public Long getCancelledOrders() { return cancelledOrders; }
    public void setCancelledOrders(Long cancelledOrders) { this.cancelledOrders = cancelledOrders; }

    public BigDecimal getAverageOrderValue() { return averageOrderValue; }
    public void setAverageOrderValue(BigDecimal averageOrderValue) { this.averageOrderValue = averageOrderValue; }
}
