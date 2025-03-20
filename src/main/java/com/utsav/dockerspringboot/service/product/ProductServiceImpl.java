package com.utsav.dockerspringboot.service.product;

import com.utsav.dockerspringboot.exception.ProductNotFoundException;
import com.utsav.dockerspringboot.model.Product;
import com.utsav.dockerspringboot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;



    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProduct(Long id) {
        if(productRepository.existsById(id)){
            return productRepository.findById(id).get();
        }else{
            throw new ProductNotFoundException("Product not found");
        }
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        if(productRepository.existsById(id)){
            Product productToUpdate = productRepository.findById(id).get();
            productToUpdate.setName(product.getName());
            productToUpdate.setPrice(product.getPrice());
            productToUpdate.setDescription(product.getDescription());
            productToUpdate.setCategory(product.getCategory());
            productToUpdate.setImageUrl(product.getImageUrl());
            productToUpdate.setIsFeatured(product.getIsFeatured());
            productToUpdate.setLowStockThreshold(product.getLowStockThreshold());
            return productRepository.save(productToUpdate);
        }else {
            throw new ProductNotFoundException("Product not found");
        }
    }

    @Override
    public void deleteProduct(Long id) {
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
        }else {
            throw new ProductNotFoundException("Product not found");
        }

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
