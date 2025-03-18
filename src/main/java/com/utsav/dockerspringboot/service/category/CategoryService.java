package com.utsav.dockerspringboot.service.category;

import com.utsav.dockerspringboot.model.Category;

import java.util.List;

public interface CategoryService {
    Category saveCategory(Category category);
    List<Category> findAllCategory();
    Category findCategoryById(Long id);
    Category updateCategory(Category category);
    void deleteCategory(Long id);
}
