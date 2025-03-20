package com.utsav.dockerspringboot.service.coupon;
import com.utsav.dockerspringboot.exception.CouponNotFoundException;
import com.utsav.dockerspringboot.model.Coupon;
import com.utsav.dockerspringboot.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public Coupon getCouponById(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found with id: " + couponId));
    }

    @Override
    public Coupon getCouponByCode(String code) {
        return couponRepository.findByCode(code)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found with code: " + code));
    }

    @Override
    public List<Coupon> getActiveCoupons() {
        LocalDateTime now = LocalDateTime.now();
        return couponRepository.findByValidFromLessThanEqualAndValidToGreaterThanEqual(now, now);
    }

    @Override
    public Coupon updateCoupon(Long couponId, Coupon couponDetails) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found with id: " + couponId));

        coupon.setCode(couponDetails.getCode());
        coupon.setDiscountType(couponDetails.getDiscountType());
        coupon.setDiscountValue(couponDetails.getDiscountValue());
        coupon.setValidFrom(couponDetails.getValidFrom());
        coupon.setValidTo(couponDetails.getValidTo());
        coupon.setMaxUses(couponDetails.getMaxUses());
        coupon.setUsedCount(couponDetails.getUsedCount());

        return couponRepository.save(coupon);
    }

    @Override
    public void deleteCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found with id: " + couponId));
        couponRepository.delete(coupon);
    }

    @Override
    public boolean validateCoupon(String code) {
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found with code: " + code));

        LocalDateTime now = LocalDateTime.now();
        return coupon.getValidFrom().isBefore(now) &&
                coupon.getValidTo().isAfter(now) &&
                coupon.getUsedCount() < coupon.getMaxUses();
    }
}