package com.utsav.dockerspringboot.controller;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class TestUserController {

    @GetMapping("/hello")
    public String userController(){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        return "Hello World";

    }

}
