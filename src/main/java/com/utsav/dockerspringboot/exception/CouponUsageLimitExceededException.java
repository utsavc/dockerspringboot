package com.utsav.dockerspringboot.exception;

public class CouponUsageLimitExceededException extends Exception {
    public CouponUsageLimitExceededException(String message) {
        super(message);
    }
}
