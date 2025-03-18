package com.utsav.dockerspringboot.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StockHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_history_id")
    private Long stockHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Product product;

    @Column(name = "change_quantity", nullable = false)
    private Integer changeQuantity;

    @Column(name = "reason", nullable = false)
    private String reason; // e.g., "Purchase", "Restock"

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;



}
