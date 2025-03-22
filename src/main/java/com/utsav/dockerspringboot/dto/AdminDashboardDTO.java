package com.utsav.dockerspringboot.dto;

import lombok.Data;

@Data
public class AdminDashboardDTO {
    private OrderMetrics orderMetrics;
    private UserMetrics userMetrics;
    private ProductMetrics productMetrics;
    private CouponMetrics couponMetrics;
    private ReturnRequestMetrics returnRequestMetrics;
    private RewardPointsMetrics rewardPointsMetrics;

    @Data
    public static class OrderMetrics {
        private long totalOrders;
        private long pendingOrders;
        private long shippedOrders;
        private long deliveredOrders;
        private long cancelledOrders;
        private double totalRevenue;
    }

    @Data
    public static class UserMetrics {
        private long totalUsers;
        private long newUsersLast7Days;
        private long newUsersLast30Days;
    }

    @Data
    public static class ProductMetrics {
        private long totalProducts;
        private long lowStockProducts;
        private long featuredProducts;
    }

    @Data
    public static class CouponMetrics {
        private long totalCoupons;
        private long usedCoupons;
        private long unusedCoupons;
    }

    @Data
    public static class ReturnRequestMetrics {
        private long totalReturnRequests;
        private long pendingReturnRequests;
        private long approvedReturnRequests;
        private long rejectedReturnRequests;
    }

    @Data
    public static class RewardPointsMetrics {
        private long totalPointsEarned;
        private long totalPointsRedeemed;
    }
}