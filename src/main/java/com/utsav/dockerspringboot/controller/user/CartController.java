package com.utsav.dockerspringboot.controller.user;

import com.utsav.dockerspringboot.dto.*;
import com.utsav.dockerspringboot.model.Cart;
import com.utsav.dockerspringboot.model.Order;
import com.utsav.dockerspringboot.model.OrderItem;
import com.utsav.dockerspringboot.repository.CartRepository;
import com.utsav.dockerspringboot.service.cart.CartService;
import com.utsav.dockerspringboot.service.cart.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("/add-to-cart")
    public ResponseEntity<CartDto> addToCart(@RequestBody CartDto cartDto) {
        Cart cart = cartService.addToCart(cartDto);


        // Map the Cart entity to a CartDto
        CartDto dto = new CartDto();
        dto.setProductId(cart.getCartId());
        dto.setQuantity(cart.getQuantity());

        // Map Product details
        ProductDto productDto = new ProductDto();
        productDto.setProductId(cart.getProduct().getProductId());
        productDto.setName(cart.getProduct().getName());
        productDto.setPrice(cart.getProduct().getPrice());

        dto.setProductDto(productDto);

        // Map User details
        UserDto userDto = new UserDto();
        userDto.setUserId(cart.getUser().getUserId());
        userDto.setUsername(cart.getUser().getUsername());
        dto.setUserDto(userDto);


        return ResponseEntity.ok(dto);
    }


    @PostMapping("/checkout")
    public ResponseEntity<OrderDto> checkout(@RequestBody CheckoutRequest checkoutRequest) {
        Order order = checkoutService.checkout(checkoutRequest);


        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getOrderId());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setUserId(order.getUser().getUserId());
        orderDto.setPaymentMethod(order.getPaymentMethod());
        orderDto.setStatus(order.getStatus());
        orderDto.setTotalAmount(order.getTotalAmount());
        orderDto.setShippingAddress(order.getShippingAddress());

        // Map the list of OrderItems to OrderItemDto
        if (order.getOrderItems() != null) {
            orderDto.setOrderItems(
                    order.getOrderItems().stream()
                            .map(this::mapOrderItemToDto) // Helper method to map OrderItem to OrderItemDto
                            .toList()
            );
        }


        return ResponseEntity.ok(orderDto);
    }



    private OrderItemDto mapOrderItemToDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setOrderItemId(orderItem.getOrderItemId());
        orderItemDto.setProductId(orderItem.getProduct().getProductId()); // Extract only the productId
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPriceAtPurchase(orderItem.getPriceAtPurchase());
        return orderItemDto;
    }


}