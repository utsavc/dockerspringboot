package com.utsav.dockerspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long productId;

    private String name;

    private String description;

    private Double price;

    private Integer stockQuantity;

    private Integer lowStockThreshold;

    private String imageUrl;

    private Boolean isFeatured;

    private LocalDateTime createdAt;

    private Long categoryId;

}