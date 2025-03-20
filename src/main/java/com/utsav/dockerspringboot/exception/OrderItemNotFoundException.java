package com.utsav.dockerspringboot.exception;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException(String s) {
        super(s);
    }
}
