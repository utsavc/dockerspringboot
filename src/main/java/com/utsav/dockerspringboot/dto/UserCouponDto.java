package com.utsav.dockerspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCouponDto {

    private Long userCouponId;

    private Long userId; // Include only the ID of the associated user

    private Long couponId; // Include only the ID of the associated coupon

    private LocalDateTime usedAt;
}