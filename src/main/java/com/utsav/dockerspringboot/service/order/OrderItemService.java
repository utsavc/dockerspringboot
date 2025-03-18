package com.utsav.dockerspringboot.service.order;

import com.utsav.dockerspringboot.model.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItem createOrderItem(OrderItem orderItem);
    List<OrderItem> getOrderItemsByOrderId(Long orderId);
    OrderItem updateOrderItem(Long orderItemId, Integer quantity, Double priceAtPurchase);
    void deleteOrderItem(Long orderItemId);
}