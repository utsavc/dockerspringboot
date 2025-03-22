package com.utsav.dockerspringboot.controller.admin;

import com.utsav.dockerspringboot.dto.AdminDashboardDTO;
import com.utsav.dockerspringboot.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService dashboardService;

    // Fetch dashboard metrics
    @GetMapping
    public ResponseEntity<AdminDashboardDTO> getDashboardMetrics() {
        AdminDashboardDTO dashboardMetrics = dashboardService.getDashboardMetrics();
        return ResponseEntity.ok(dashboardMetrics);
    }
}