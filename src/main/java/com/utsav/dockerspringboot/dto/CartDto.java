package com.utsav.dockerspringboot.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CartDto {
    private Long productId;
    private Integer quantity;
    private LocalDateTime addedAt;
    private ProductDto productDto;
    private UserDto userDto;


}
