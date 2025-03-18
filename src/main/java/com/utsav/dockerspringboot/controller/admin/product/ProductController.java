package com.utsav.dockerspringboot.controller.admin.product;

import com.utsav.dockerspringboot.model.Product;
import com.utsav.dockerspringboot.model.StockHistory;
import com.utsav.dockerspringboot.service.product.ProductService;
import com.utsav.dockerspringboot.service.product.StockHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private StockHistoryService stockHistoryService;

    @PostMapping("/create")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.updateProduct(product.getProductId(), product), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product has been deleted",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }


    @PostMapping("/add-stock")
    public ResponseEntity<StockHistory> addStock(@RequestBody StockHistory stockHistory) {
        return new ResponseEntity<>(stockHistoryService.saveStockHistory(stockHistory), HttpStatus.CREATED);
    }





}
