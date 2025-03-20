package com.utsav.dockerspringboot.service.coupon;

import com.utsav.dockerspringboot.exception.CouponNotFoundException;
import com.utsav.dockerspringboot.exception.UserCouponNotFoundException;
import com.utsav.dockerspringboot.exception.CouponAlreadyUsedException;
import com.utsav.dockerspringboot.exception.CouponExpiredException;
import com.utsav.dockerspringboot.exception.CouponUsageLimitExceededException;
import com.utsav.dockerspringboot.model.Coupon;
import com.utsav.dockerspringboot.model.User;
import com.utsav.dockerspringboot.model.UserCoupon;
import com.utsav.dockerspringboot.repository.CouponRepository;
import com.utsav.dockerspringboot.repository.UserCouponRepository;
import com.utsav.dockerspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserCouponServiceImpl implements UserCouponService {

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserCoupon useCoupon(Long userId, Long couponId) throws Exception {
        // Check if the user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Check if the coupon exists and is valid
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found with id: " + couponId));

        // Validate the coupon
        validateCoupon(coupon);

        // Check if the user has already used this coupon
        if (userCouponRepository.findByUserUserIdAndCouponCouponId(userId, couponId).isPresent()) {
            throw new CouponAlreadyUsedException("User has already used this coupon.");
        }

        // Create a new UserCoupon entry
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUser(user);
        userCoupon.setCoupon(coupon);
        userCoupon.setUsedAt(LocalDateTime.now());

        // Increment the coupon's used count
        coupon.setUsedCount(coupon.getUsedCount() + 1);
        couponRepository.save(coupon);

        return userCouponRepository.save(userCoupon);
    }

    @Override
    public List<UserCoupon> getUserCouponsByUserId(Long userId) {
        return userCouponRepository.findByUserUserId(userId);
    }

    @Override
    public List<UserCoupon> getUserCouponsByCouponId(Long couponId) {
        return userCouponRepository.findByCouponCouponId(couponId);
    }

    @Override
    public void revokeUserCoupon(Long userCouponId) {
        UserCoupon userCoupon = userCouponRepository.findById(userCouponId)
                .orElseThrow(() -> new UserCouponNotFoundException("UserCoupon not found with id: " + userCouponId));

        // Decrement the coupon's used count
        Coupon coupon = userCoupon.getCoupon();
        coupon.setUsedCount(coupon.getUsedCount() - 1);
        couponRepository.save(coupon);

        // Delete the UserCoupon entry
        userCouponRepository.delete(userCoupon);
    }

    private void validateCoupon(Coupon coupon) throws CouponExpiredException, CouponUsageLimitExceededException {
        LocalDateTime now = LocalDateTime.now();

        // Check if the coupon is expired
        if (now.isBefore(coupon.getValidFrom())) {
            throw new CouponExpiredException("Coupon is not yet valid.");
        }
        if (now.isAfter(coupon.getValidTo())) {
            throw new CouponExpiredException("Coupon has expired.");
        }

        // Check if the coupon usage limit is exceeded
        if (coupon.getUsedCount() >= coupon.getMaxUses()) {
            throw new CouponUsageLimitExceededException("Coupon usage limit has been reached.");
        }
    }
}