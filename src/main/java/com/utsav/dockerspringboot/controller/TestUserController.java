package com.utsav.dockerspringboot.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class TestUserController {

    @GetMapping("/hello")
    public String userController(){
        return "Hello World";

    }

}
