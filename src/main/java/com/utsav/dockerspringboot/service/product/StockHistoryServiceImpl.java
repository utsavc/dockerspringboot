package com.utsav.dockerspringboot.service.product;

import com.utsav.dockerspringboot.exception.ProductNotFoundException;
import com.utsav.dockerspringboot.model.Product;
import com.utsav.dockerspringboot.model.StockHistory;
import com.utsav.dockerspringboot.repository.ProductRepository;
import com.utsav.dockerspringboot.repository.StockHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockHistoryServiceImpl implements StockHistoryService {

    @Autowired
    private StockHistoryRepository stockHistoryRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<StockHistory> getStockHistory() {
        return List.of();
    }

    @Override
    public StockHistory saveStockHistory(StockHistory stockHistory) {
        if(productRepository.existsById(stockHistory.getProduct().getProductId())) {
            Product product = productRepository.findById(stockHistory.getProduct().getProductId()).get();
            Integer stockQuantity = product.getStockQuantity();
            product.setStockQuantity(stockQuantity+stockHistory.getChangeQuantity());
            productRepository.save(product);
            return stockHistoryRepository.save(stockHistory);
        }else {
            throw new ProductNotFoundException("Product not found");
        }

    }

    @Override
    public StockHistory updateStockHistory(StockHistory stockHistory) {
        return null;
    }

    @Override
    public StockHistory getStockHistoryById(Long id) {
        return null;
    }
}
