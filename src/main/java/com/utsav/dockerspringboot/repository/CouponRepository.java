package com.utsav.dockerspringboot.repository;

import com.utsav.dockerspringboot.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCode(String code); // Find a coupon by its code
    List<Coupon> findByValidFromLessThanEqualAndValidToGreaterThanEqual(LocalDateTime start, LocalDateTime end); // Find active coupons
}