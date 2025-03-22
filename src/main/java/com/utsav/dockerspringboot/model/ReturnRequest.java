package com.utsav.dockerspringboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "return_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReturnRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_request_id")
    private Long returnRequestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id", referencedColumnName = "order_item_id", nullable = false)
    private OrderItem orderItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @Column(name = "reason", nullable = false)
    private String reason; // e.g., "Defective", "Wrong Item", "Changed Mind"

    @Column(name = "status", nullable = false)
    private String status; // e.g., "PENDING", "APPROVED", "REJECTED", "COMPLETED"

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    @Column(name = "refund_amount")
    private Double refundAmount;

    @Column(name = "refund_method")
    private String refundMethod; // e.g., "STORE_CREDIT", "ORIGINAL_PAYMENT"
}