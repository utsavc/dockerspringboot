package com.utsav.dockerspringboot.service.coupon;
import com.utsav.dockerspringboot.dto.CouponDto;
import com.utsav.dockerspringboot.model.Coupon;

import java.util.List;

public interface CouponService {
    Coupon createCoupon(CouponDto couponDto);
    Coupon getCouponById(Long couponId);
    Coupon getCouponByCode(String code);
    List<Coupon> getActiveCoupons();
    Coupon updateCoupon(Long couponId, Coupon couponDetails);
    void deleteCoupon(Long couponId);
    boolean validateCoupon(String code);
}