package com.utsav.dockerspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private Long orderItemId;

    private Long productId;

    private Integer quantity;

    private Double priceAtPurchase;

    private Long orderId;
}