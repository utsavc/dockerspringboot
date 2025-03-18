package com.utsav.dockerspringboot.controller.user;

import com.utsav.dockerspringboot.model.Cart;
import com.utsav.dockerspringboot.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody Cart cart) {
        Cart savedCart = cartService.addToCart(cart);
        return ResponseEntity.ok(savedCart);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Cart>> getCartByUserId(@PathVariable Long userId) {
        List<Cart> carts = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(carts);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long cartId) {
        cartService.removeFromCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cartId}/quantity")
    public ResponseEntity<Cart> updateCartQuantity(@PathVariable Long cartId, @RequestParam Integer quantity) {
        Cart updatedCart = cartService.updateCartQuantity(cartId, quantity);
        return ResponseEntity.ok(updatedCart);
    }
}