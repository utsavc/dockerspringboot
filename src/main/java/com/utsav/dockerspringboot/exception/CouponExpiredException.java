package com.utsav.dockerspringboot.exception;

public class CouponExpiredException extends Exception {
    public CouponExpiredException(String message) {
        super(message);
    }
}
