package com.utsav.dockerspringboot.exception;

public class UserCouponNotFoundException extends RuntimeException {
    public UserCouponNotFoundException(String message) {
        super(message);
    }
}