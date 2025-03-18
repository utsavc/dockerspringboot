package com.utsav.dockerspringboot.service.category;

import com.utsav.dockerspringboot.model.Category;
import com.utsav.dockerspringboot.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryById(Long id) {
        if (categoryRepository.existsById(id)) {
            return categoryRepository.findById(id).get();
        }else {
            throw new RuntimeException("Category not found");
        }
    }

    @Override
    public Category updateCategory(Category category) {
        if (categoryRepository.existsById(category.getCategoryId())){
            return categoryRepository.save(category);
        }else {
            throw new RuntimeException("Category not found");
        }
    }

    @Override
    public void deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        }else {
            throw new RuntimeException("Category not found");
        }
    }
}
