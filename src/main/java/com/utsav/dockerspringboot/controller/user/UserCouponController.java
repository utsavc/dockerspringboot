package com.utsav.dockerspringboot.controller.user;

import com.utsav.dockerspringboot.model.UserCoupon;
import com.utsav.dockerspringboot.service.coupon.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-coupons")
public class UserCouponController {

    @Autowired
    private UserCouponService userCouponService;

    @PostMapping("/use")
    public ResponseEntity<UserCoupon> useCoupon(@RequestParam Long userId, @RequestParam Long couponId) throws Exception {
        UserCoupon userCoupon = userCouponService.useCoupon(userId, couponId);
        return ResponseEntity.ok(userCoupon);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserCoupon>> getUserCouponsByUserId(@PathVariable Long userId) {
        List<UserCoupon> userCoupons = userCouponService.getUserCouponsByUserId(userId);
        return ResponseEntity.ok(userCoupons);
    }

    @GetMapping("/coupon/{couponId}")
    public ResponseEntity<List<UserCoupon>> getUserCouponsByCouponId(@PathVariable Long couponId) {
        List<UserCoupon> userCoupons = userCouponService.getUserCouponsByCouponId(couponId);
        return ResponseEntity.ok(userCoupons);
    }

    @DeleteMapping("/{userCouponId}")
    public ResponseEntity<Void> revokeUserCoupon(@PathVariable Long userCouponId) {
        userCouponService.revokeUserCoupon(userCouponId);
        return ResponseEntity.noContent().build();
    }
}