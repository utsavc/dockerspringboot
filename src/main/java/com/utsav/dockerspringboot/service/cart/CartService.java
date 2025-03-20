package com.utsav.dockerspringboot.service.cart;

import com.utsav.dockerspringboot.dto.CartDto;
import com.utsav.dockerspringboot.model.Cart;

public interface CartService {
    Cart addToCart(CartDto cartDto);
}