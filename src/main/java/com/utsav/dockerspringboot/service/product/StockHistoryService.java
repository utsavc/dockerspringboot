package com.utsav.dockerspringboot.service.product;

import com.utsav.dockerspringboot.model.StockHistory;

import java.util.List;

public interface StockHistoryService {
    List<StockHistory> getStockHistory();
    StockHistory saveStockHistory(StockHistory stockHistory);
    StockHistory updateStockHistory(StockHistory stockHistory);
    StockHistory getStockHistoryById(Long id);
}
