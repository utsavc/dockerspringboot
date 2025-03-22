package com.utsav.dockerspringboot.controller;

import com.utsav.dockerspringboot.dto.ReturnRequestDTO;
import com.utsav.dockerspringboot.service.returnrequest.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/returns")
public class ReturnController {

    @Autowired
    private ReturnService returnService;

    @PostMapping
    public ResponseEntity<String> initiateReturn(@RequestBody ReturnRequestDTO returnRequestDTO) {
        try {
            returnService.initiateReturn(returnRequestDTO.getOrderItemId(), returnRequestDTO.getReason());
            return ResponseEntity.ok("Return request initiated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}