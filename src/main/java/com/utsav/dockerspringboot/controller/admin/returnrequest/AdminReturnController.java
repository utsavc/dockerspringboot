package com.utsav.dockerspringboot.controller.admin.returnrequest;

import com.utsav.dockerspringboot.service.returnrequest.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/returns")
public class AdminReturnController {

    @Autowired
    private ReturnService returnService;

    @PostMapping("/{returnRequestId}/process")
    public ResponseEntity<String> processReturnRequest(
            @PathVariable Long returnRequestId,
            @RequestParam String action, // "APPROVE" or "REJECT"
            @RequestParam(required = false) String refundMethod) { // Optional: "STORE_CREDIT" or "ORIGINAL_PAYMENT"

        try {
            returnService.processReturn(returnRequestId, action, refundMethod);
            return ResponseEntity.ok("Return request processed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}