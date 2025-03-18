package com.utsav.dockerspringboot.service.cart;

import com.utsav.dockerspringboot.model.Cart;

import java.util.List;

public interface CartService {
    Cart addToCart(Cart cart);
    List<Cart> getCartByUserId(Long userId);
    void removeFromCart(Long cartId);
    Cart updateCartQuantity(Long cartId, Integer quantity);
}