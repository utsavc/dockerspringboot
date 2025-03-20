package com.utsav.dockerspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private Long orderItemId;

    private Long productId; // Include only the ID of the associated product

    private Integer quantity;

    private Double priceAtPurchase;

    // Optional: Include orderId if needed
    private Long orderId; // Include only the ID of the associated order
}