package com.utsav.dockerspringboot.service.cart;

import com.utsav.dockerspringboot.exceptions.CartNotFoundException;
import com.utsav.dockerspringboot.model.Cart;
import com.utsav.dockerspringboot.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart addToCart(Cart cart) {
        cart.setAddedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserUserId(userId);
    }

    @Override
    public void removeFromCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public Cart updateCartQuantity(Long cartId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }
}