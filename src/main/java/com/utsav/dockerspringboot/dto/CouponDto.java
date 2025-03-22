package com.utsav.dockerspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponDto {

    private Long couponId;

    private String code;

    private String discountType;

    private Double discountValue;

    private LocalDateTime validFrom;

    private LocalDateTime validTo;

    private Integer maxUses;

    private Integer usedCount;

    // Optional: Include simplified userCoupons if needed
    private List<UserCouponDto> userCoupons;
}