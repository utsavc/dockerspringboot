package com.utsav.dockerspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long orderId;

    private Long userId;

    private LocalDateTime orderDate;

    private Double totalAmount;

    private String status;

    private String shippingAddress;

    private String paymentMethod;

    private List<OrderItemDto> orderItems;
}