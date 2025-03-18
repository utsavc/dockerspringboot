package com.utsav.dockerspringboot.repository;

import com.utsav.dockerspringboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
}
