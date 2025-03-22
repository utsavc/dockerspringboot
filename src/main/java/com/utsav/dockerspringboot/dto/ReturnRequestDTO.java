package com.utsav.dockerspringboot.dto;

import lombok.Data;

@Data
public class ReturnRequestDTO {
    private Long orderItemId;
    private String reason; // e.g., "Defective", "Wrong Item", "Changed Mind"
}