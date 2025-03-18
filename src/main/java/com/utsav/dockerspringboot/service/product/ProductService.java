package com.utsav.dockerspringboot.service.product;

import com.utsav.dockerspringboot.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product getProduct(Long id);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    List<Product> getAllProducts();
}
