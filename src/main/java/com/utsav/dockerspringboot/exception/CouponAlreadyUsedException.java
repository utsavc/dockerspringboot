package com.utsav.dockerspringboot.exception;

public class CouponAlreadyUsedException extends Exception {
    public CouponAlreadyUsedException(String message) {
        super(message);
    }
}
