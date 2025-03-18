package com.utsav.dockerspringboot.exceptions;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException(String s) {
        super(s);
    }
}
