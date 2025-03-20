package com.utsav.dockerspringboot.service.order;

import com.utsav.dockerspringboot.exception.OrderItemNotFoundException;
import com.utsav.dockerspringboot.model.OrderItem;
import com.utsav.dockerspringboot.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderOrderId(orderId);
    }

    @Override
    public OrderItem updateOrderItem(Long orderItemId, Integer quantity, Double priceAtPurchase) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new OrderItemNotFoundException("OrderItem not found with id: " + orderItemId));

        orderItem.setQuantity(quantity);
        orderItem.setPriceAtPurchase(priceAtPurchase);
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new OrderItemNotFoundException("OrderItem not found with id: " + orderItemId));
        orderItemRepository.delete(orderItem);
    }
}