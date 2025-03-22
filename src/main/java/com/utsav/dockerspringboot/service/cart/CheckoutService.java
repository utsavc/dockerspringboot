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
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private RewardPointsRepository rewardPointsRepository;

    @Autowired
    private MyUtils myUtils;


    @Transactional
    public Order checkout(CheckoutRequest checkoutRequest) {
        // Fetch the current user
        User user = fetchCurrentUser();

        // Fetch cart items for the user
        List<Cart> cartItems = fetchUserCartItems(user);

        // Calculate the original total amount
        double originalTotalAmount = calculateOriginalTotal(cartItems);

        // Apply coupon discount (if provided)
        Coupon coupon = null;
        double discountAmount = 0;
        if (checkoutRequest.getCouponCode() != null) {
            coupon = applyCoupon(checkoutRequest.getCouponCode(), user, originalTotalAmount);
            discountAmount = calculateDiscount(coupon, originalTotalAmount);
        }

        // Redeem reward points (if requested)
        double redeemedRewardPointsValue = redeemRewardPoints(checkoutRequest.getRewardPointsRedeemed(), user);

        // Calculate the final total amount
        double totalAmount = calculateFinalTotal(originalTotalAmount, discountAmount, redeemedRewardPointsValue);

        // Create the order
        Order order = createOrder(user, checkoutRequest, originalTotalAmount, discountAmount, totalAmount);

        // Process cart items and update stock
        processCartItems(cartItems, order);

        // Clear the cart
        clearCart(cartItems);

        // Accumulate reward points based on the final order total
        accumulateRewardPoints(user, totalAmount);

        return order;
    }



    private User fetchCurrentUser() {
        return userRepository.findByUsername(myUtils.getCurrentUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private List<Cart> fetchUserCartItems(User user) {
        List<Cart> cartItems = cartRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        return cartItems;
    }

    private double calculateOriginalTotal(List<Cart> cartItems) {
        return cartItems.stream()
                .mapToDouble(cart -> cart.getProduct().getPrice() * cart.getQuantity())
                .sum();
    }

    private Coupon applyCoupon(String couponCode, User user, double originalTotalAmount) {
        Coupon coupon = couponRepository.findByCode(couponCode)
                .orElseThrow(() -> new RuntimeException("Invalid coupon code"));

        if (coupon.getUsedCount() >= coupon.getMaxUses()) {
            throw new RuntimeException("Coupon has reached its maximum usage limit");
        }

        if (LocalDateTime.now().isBefore(coupon.getValidFrom()) || LocalDateTime.now().isAfter(coupon.getValidTo())) {
            throw new RuntimeException("Coupon is not valid at this time");
        }

        boolean hasUserUsedCoupon = userCouponRepository.existsByUserAndCoupon(user, coupon);
        if (hasUserUsedCoupon) {
            throw new RuntimeException("You have already used this coupon");
        }

        // Track coupon usage
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUser(user);
        userCoupon.setCoupon(coupon);
        userCoupon.setUsedAt(LocalDateTime.now());
        userCouponRepository.save(userCoupon);

        // Increment coupon usage count
        coupon.setUsedCount(coupon.getUsedCount() + 1);
        couponRepository.save(coupon);

        return coupon;
    }


    private double redeemRewardPoints(Integer rewardPointsRedeemed, User user) {
        if (rewardPointsRedeemed == null || rewardPointsRedeemed == 0) {
            return 0;
        }

        RewardPoints rewardPoints = user.getRewardPoints();
        int pointsAvailable = rewardPoints.getPointsEarned() - rewardPoints.getPointsRedeemed();

        if (rewardPointsRedeemed > pointsAvailable) {
            throw new RuntimeException("Insufficient reward points");
        }

        // Convert points to monetary value (e.g., 1 point = $0.01)
        double redeemedRewardPointsValue = rewardPointsRedeemed * 0.01;

        // Update reward points
        rewardPoints.setPointsRedeemed(rewardPoints.getPointsRedeemed() + rewardPointsRedeemed);
        rewardPointsRepository.save(rewardPoints);

        return redeemedRewardPointsValue;
    }


    private double calculateFinalTotal(double originalTotalAmount, double discountAmount, double redeemedRewardPointsValue) {
        return originalTotalAmount - discountAmount - redeemedRewardPointsValue;
    }


    private Order createOrder(User user, CheckoutRequest checkoutRequest, double originalTotalAmount, double discountAmount, double totalAmount) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setOriginalTotalAmount(originalTotalAmount);
        order.setDiscountAmount(discountAmount);
        order.setTotalAmount(totalAmount);
        order.setStatus("PENDING");
        order.setShippingAddress(checkoutRequest.getShippingAddress());
        order.setPaymentMethod(checkoutRequest.getPaymentMethod());
        return orderRepository.save(order);
    }


    private void processCartItems(List<Cart> cartItems, Order order) {
        for (Cart cart : cartItems) {
            Product product = cart.getProduct();

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
    }


    private void clearCart(List<Cart> cartItems) {
        cartRepository.deleteAll(cartItems);
    }

    private void accumulateRewardPoints(User user, double totalAmount) {
        int pointsToAdd = (int) (totalAmount * 10);
        RewardPoints rewardPoints = user.getRewardPoints();
        rewardPoints.setPointsEarned(rewardPoints.getPointsEarned() + pointsToAdd);
        rewardPoints.setLastUpdated(LocalDateTime.now());
        rewardPointsRepository.save(rewardPoints);
    }



    // Helper method to calculate coupon discount
    private double calculateDiscount(Coupon coupon, double totalAmount) {
        if ("PERCENTAGE".equalsIgnoreCase(coupon.getDiscountType())) {
            return totalAmount * (coupon.getDiscountValue() / 100);
        } else if ("FIXED".equalsIgnoreCase(coupon.getDiscountType())) {
            return coupon.getDiscountValue();
        }
        return 0;
    }
}


