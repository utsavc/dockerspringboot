package com.utsav.dockerspringboot.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long userId;
    private String username;
    private String address;
}
