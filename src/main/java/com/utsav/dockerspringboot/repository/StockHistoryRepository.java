package com.utsav.dockerspringboot.repository;

import com.utsav.dockerspringboot.model.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
}
