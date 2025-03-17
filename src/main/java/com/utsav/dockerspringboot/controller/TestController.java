package com.utsav.dockerspringboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/user")
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
