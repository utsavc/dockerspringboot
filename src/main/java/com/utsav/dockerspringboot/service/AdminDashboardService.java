package com.utsav.dockerspringboot.service;

import com.utsav.dockerspringboot.dto.AdminDashboardDTO;
import com.utsav.dockerspringboot.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminDashboardService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ReturnRequestRepository returnRequestRepository;

    @Autowired
    private RewardPointsRepository rewardPointsRepository;

    public AdminDashboardDTO getDashboardMetrics() {
        AdminDashboardDTO dashboardDTO = new AdminDashboardDTO();

        // Populate Order Metrics
        populateOrderMetrics(dashboardDTO);

        // Populate User Metrics
        populateUserMetrics(dashboardDTO);

        // Populate Product Metrics
        populateProductMetrics(dashboardDTO);

        // Populate Coupon Metrics
        populateCouponMetrics(dashboardDTO);

        // Populate Return Request Metrics
        populateReturnRequestMetrics(dashboardDTO);

        // Populate Reward Points Metrics
        populateRewardPointsMetrics(dashboardDTO);

        return dashboardDTO;
    }

    private void populateOrderMetrics(AdminDashboardDTO dashboardDTO) {
        AdminDashboardDTO.OrderMetrics orderMetrics = new AdminDashboardDTO.OrderMetrics();
        orderMetrics.setTotalOrders(orderRepository.count());
        orderMetrics.setPendingOrders(orderRepository.countByStatus("PENDING"));
        orderMetrics.setShippedOrders(orderRepository.countByStatus("SHIPPED"));
        orderMetrics.setDeliveredOrders(orderRepository.countByStatus("DELIVERED"));
        orderMetrics.setCancelledOrders(orderRepository.countByStatus("CANCELLED"));
        orderMetrics.setTotalRevenue(orderRepository.calculateTotalRevenue());
        dashboardDTO.setOrderMetrics(orderMetrics);
    }

    private void populateUserMetrics(AdminDashboardDTO dashboardDTO) {
        AdminDashboardDTO.UserMetrics userMetrics = new AdminDashboardDTO.UserMetrics();
        userMetrics.setTotalUsers(userRepository.count());
        userMetrics.setNewUsersLast7Days(userRepository.countByCreatedAtAfter(LocalDateTime.now().minusDays(7)));
        userMetrics.setNewUsersLast30Days(userRepository.countByCreatedAtAfter(LocalDateTime.now().minusDays(30)));
        dashboardDTO.setUserMetrics(userMetrics);
    }

    private void populateProductMetrics(AdminDashboardDTO dashboardDTO) {
        AdminDashboardDTO.ProductMetrics productMetrics = new AdminDashboardDTO.ProductMetrics();
        productMetrics.setTotalProducts(productRepository.count());
        productMetrics.setLowStockProducts(productRepository.countByStockQuantityLessThan(10)); // Example threshold
        productMetrics.setFeaturedProducts(productRepository.countByIsFeaturedTrue());
        dashboardDTO.setProductMetrics(productMetrics);
    }

    private void populateCouponMetrics(AdminDashboardDTO dashboardDTO) {
        AdminDashboardDTO.CouponMetrics couponMetrics = new AdminDashboardDTO.CouponMetrics();
        couponMetrics.setTotalCoupons(couponRepository.count());
        couponMetrics.setUsedCoupons(couponRepository.countUsedCoupons());
        couponMetrics.setUnusedCoupons(couponRepository.countUnusedCoupons());
        dashboardDTO.setCouponMetrics(couponMetrics);
    }

    private void populateReturnRequestMetrics(AdminDashboardDTO dashboardDTO) {
        AdminDashboardDTO.ReturnRequestMetrics returnRequestMetrics = new AdminDashboardDTO.ReturnRequestMetrics();
        returnRequestMetrics.setTotalReturnRequests(returnRequestRepository.count());
        returnRequestMetrics.setPendingReturnRequests(returnRequestRepository.countByStatus("PENDING"));
        returnRequestMetrics.setApprovedReturnRequests(returnRequestRepository.countByStatus("APPROVED"));
        returnRequestMetrics.setRejectedReturnRequests(returnRequestRepository.countByStatus("REJECTED"));
        dashboardDTO.setReturnRequestMetrics(returnRequestMetrics);
    }

    private void populateRewardPointsMetrics(AdminDashboardDTO dashboardDTO) {
        AdminDashboardDTO.RewardPointsMetrics rewardPointsMetrics = new AdminDashboardDTO.RewardPointsMetrics();
        List<Object[]> result = rewardPointsRepository.getTotalPointsEarnedAndRedeemed();
        if (!result.isEmpty()) {
            Object[] row = result.get(0);
            rewardPointsMetrics.setTotalPointsEarned((Long) row[0]);
            rewardPointsMetrics.setTotalPointsRedeemed((Long) row[1]);
        }
        dashboardDTO.setRewardPointsMetrics(rewardPointsMetrics);
    }
}