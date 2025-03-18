package com.utsav.dockerspringboot.repository;

import com.utsav.dockerspringboot.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
