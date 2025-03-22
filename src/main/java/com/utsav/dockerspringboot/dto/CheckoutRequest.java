package com.utsav.dockerspringboot.dto;

import lombok.Data;

@Data
public class CheckoutRequest {
    private String shippingAddress; // Required: Address where the order will be shipped
    private String paymentMethod;  // Required: Payment method (e.g., "CREDIT_CARD", "PAYPAL")
    private String couponCode;     // Optional: Coupon code to apply a discount
    private Integer rewardPointsRedeemed; // Optional: Number of reward points to redeem
}