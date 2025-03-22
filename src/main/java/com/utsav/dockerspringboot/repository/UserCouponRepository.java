package com.utsav.dockerspringboot.repository;

import com.utsav.dockerspringboot.model.Coupon;
import com.utsav.dockerspringboot.model.Order;
import com.utsav.dockerspringboot.model.User;
import com.utsav.dockerspringboot.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    List<UserCoupon> findByUserUserId(Long userId); // Find all user coupons by user ID
    List<UserCoupon> findByCouponCouponId(Long couponId); // Find all user coupons by coupon ID
    Optional<UserCoupon> findByUserUserIdAndCouponCouponId(Long userId, Long couponId); // Check if a user has already used a coupon

    boolean existsByUserAndCoupon(User user, Coupon coupon);
    List<UserCoupon> findByOrder(Order order);
}