package com.utsav.dockerspringboot.service.coupon;

import com.utsav.dockerspringboot.model.UserCoupon;

import java.util.List;

public interface UserCouponService {
    UserCoupon useCoupon(Long userId, Long couponId) throws Exception; // User uses a coupon
    List<UserCoupon> getUserCouponsByUserId(Long userId); // Get all coupons used by a user
    List<UserCoupon> getUserCouponsByCouponId(Long couponId); // Get all users who used a specific coupon
    void revokeUserCoupon(Long userCouponId); // Admin revokes a user's coupon usage
}