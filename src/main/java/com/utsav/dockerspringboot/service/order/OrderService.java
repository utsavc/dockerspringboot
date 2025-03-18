package com.utsav.dockerspringboot.service.order;

import com.utsav.dockerspringboot.model.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    Order getOrderById(Long orderId);
    List<Order> getOrdersByUserId(Long userId);
    Order updateOrderStatus(Long orderId, String status);
    void deleteOrder(Long orderId);
}