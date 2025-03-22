package com.utsav.dockerspringboot.service.returnrequest;

import com.utsav.dockerspringboot.model.*;
import com.utsav.dockerspringboot.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReturnServiceImpl implements ReturnService{

    @Autowired
    private ReturnRequestRepository returnRequestRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;



    @Transactional
    public ReturnRequest initiateReturn(Long orderItemId, String reason) {
        // Fetch the order item
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        // Check if the item has already been returned
        if (Boolean.TRUE.equals(orderItem.getIsReturned())) {
            throw new RuntimeException("This item has already been returned");
        }

        // Create a return request
        ReturnRequest returnRequest = new ReturnRequest();
        returnRequest.setOrderItem(orderItem);
        returnRequest.setUser(orderItem.getOrder().getUser());
        returnRequest.setReason(reason);
        returnRequest.setStatus("PENDING");
        returnRequest.setRequestedAt(LocalDateTime.now());

        // Save the return request
        return returnRequestRepository.save(returnRequest);

        // Optionally, notify the admin about the return request
    }



    @Transactional
    public void processReturn(Long returnRequestId, String action, String refundMethod) {
        ReturnRequest returnRequest = returnRequestRepository.findById(returnRequestId)
                .orElseThrow(() -> new RuntimeException("Return request not found"));

        if (!"PENDING".equals(returnRequest.getStatus())) {
            throw new RuntimeException("Return request cannot be processed as it is not pending");
        }

        OrderItem orderItem = returnRequest.getOrderItem();

        if ("APPROVE".equalsIgnoreCase(action)) {
            // Approve the return
            returnRequest.setStatus("APPROVED");

            // Mark the order item as returned
            orderItem.setIsReturned(true);
            orderItemRepository.save(orderItem);

            // Calculate refund amount
            double refundAmount = orderItem.getPriceAtPurchase() * orderItem.getQuantity();
            returnRequest.setRefundAmount(refundAmount);
            returnRequest.setRefundMethod(refundMethod);

            // Handle refund
            handleRefund(returnRequest.getUser(), refundAmount, refundMethod);

            // Adjust stock
            adjustStock(orderItem.getProduct(), orderItem.getQuantity());

            // Adjust reward points
            adjustRewardPointsForReturn(orderItem.getOrder(), orderItem);

            // Reinstating coupons if applicable
            reinstateCouponIfUsed(orderItem.getOrder());

        } else if ("REJECT".equalsIgnoreCase(action)) {
            // Reject the return
            returnRequest.setStatus("REJECTED");
        }

        // Set resolved timestamp
        returnRequest.setResolvedAt(LocalDateTime.now());

        // Save the updated return request
        returnRequestRepository.save(returnRequest);
    }



    private void handleRefund(User user, double refundAmount, String refundMethod) {
        if ("STORE_CREDIT".equalsIgnoreCase(refundMethod)) {
            // Add store credit to the user's account
            RewardPoints rewardPoints = user.getRewardPoints();
            int pointsToAdd = (int) (refundAmount * 100); // Example: $1 = 100 points
            rewardPoints.setPointsEarned(rewardPoints.getPointsEarned() + pointsToAdd);
            rewardPoints.setLastUpdated(LocalDateTime.now());
            // Save the updated reward points
        } else if ("ORIGINAL_PAYMENT".equalsIgnoreCase(refundMethod)) {
            // Refund to the original payment method (integration with payment gateway required)
        }
    }


    private void adjustStock(Product product, int quantity) {
        product.setStockQuantity(product.getStockQuantity() + quantity);
        // Optionally, log the stock adjustment in the StockHistory table
    }


    private void adjustRewardPointsForReturn(Order order, OrderItem orderItem) {
        double productTotal = orderItem.getPriceAtPurchase() * orderItem.getQuantity();
        int pointsToDeduct = (int) (productTotal * 10); // Example: 10 points per dollar

        RewardPoints rewardPoints = order.getUser().getRewardPoints();
        rewardPoints.setPointsEarned(Math.max(0, rewardPoints.getPointsEarned() - pointsToDeduct));
        rewardPoints.setLastUpdated(LocalDateTime.now());
    }


    private void reinstateCouponIfUsed(Order order) {
        // Assuming you track coupon usage in the UserCoupon entity
        List<UserCoupon> userCoupons = userCouponRepository.findByOrder(order);
        for (UserCoupon userCoupon : userCoupons) {
            Coupon coupon = userCoupon.getCoupon();
            coupon.setUsedCount(coupon.getUsedCount() - 1); // Decrement used count
            couponRepository.save(coupon);

            // Delete the UserCoupon record
            userCouponRepository.delete(userCoupon);
        }
    }





}