package com.utsav.dockerspringboot.service.cart;

import com.utsav.dockerspringboot.dto.CheckoutRequest;
import com.utsav.dockerspringboot.model.*;
import com.utsav.dockerspringboot.repository.*;
import com.utsav.dockerspringboot.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckoutService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyUtils myUtils;


    @Transactional
    public Order checkout(CheckoutRequest checkoutRequest) {
        User user = userRepository.findByUsername(myUtils.getCurrentUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Cart> cartItems = cartRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double totalAmount = cartItems.stream()
                .mapToDouble(cart -> cart.getProduct().getPrice() * cart.getQuantity())
                .sum();

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(totalAmount);
        order.setStatus("PENDING");
        order.setShippingAddress(checkoutRequest.getShippingAddress());
        order.setPaymentMethod(checkoutRequest.getPaymentMethod());
        order = orderRepository.save(order);

        for (Cart cart : cartItems) {
            Product product = cart.getProduct();

            // Reduce stock
            if (product.getStockQuantity() < cart.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }
            product.setStockQuantity(product.getStockQuantity() - cart.getQuantity());
            productRepository.save(product);

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setPriceAtPurchase(product.getPrice());
            orderItemRepository.save(orderItem);
        }

        // Clear the cart
        cartRepository.deleteAll(cartItems);

        return order;
    }
}
