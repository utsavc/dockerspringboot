package com.utsav.dockerspringboot.controller.admin.coupon;

import com.utsav.dockerspringboot.model.Coupon;
import com.utsav.dockerspringboot.service.coupon.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
        Coupon createdCoupon = couponService.createCoupon(coupon);
        return ResponseEntity.ok(createdCoupon);
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable Long couponId) {
        Coupon coupon = couponService.getCouponById(couponId);
        return ResponseEntity.ok(coupon);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Coupon> getCouponByCode(@PathVariable String code) {
        Coupon coupon = couponService.getCouponByCode(code);
        return ResponseEntity.ok(coupon);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Coupon>> getActiveCoupons() {
        List<Coupon> activeCoupons = couponService.getActiveCoupons();
        return ResponseEntity.ok(activeCoupons);
    }

    @PutMapping("/{couponId}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable Long couponId, @RequestBody Coupon couponDetails) {
        Coupon updatedCoupon = couponService.updateCoupon(couponId, couponDetails);
        return ResponseEntity.ok(updatedCoupon);
    }

    @DeleteMapping("/{couponId}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long couponId) {
        couponService.deleteCoupon(couponId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/validate/{code}")
    public ResponseEntity<Boolean> validateCoupon(@PathVariable String code) {
        boolean isValid = couponService.validateCoupon(code);
        return ResponseEntity.ok(isValid);
    }
}