package com.melkart_api.melkart_api.service.impl;

import com.melkart_api.melkart_api.controller.dto.request.CategoryRequestDTO;
import com.melkart_api.melkart_api.model.Category;
import com.melkart_api.melkart_api.repository.CategoryRepository;
import com.melkart_api.melkart_api.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    @Override
    public Category createCategory(CategoryRequestDTO categoryRequestDTO) {
        try {
            log.info("Attempting to create category with name: {}", categoryRequestDTO.getName());

            Category category = new Category();
            category.setName(categoryRequestDTO.getName());

            categoryRepository.save(category);

            log.info("Successfully created category: {}", category.getName());

            return category;
        } catch (Exception e) {
            log.error("Error occurred while creating category with name: {}. Error: {}", categoryRequestDTO.getName(), e.getMessage(), e);
            throw new RuntimeException("Error occurred while creating category: " + categoryRequestDTO.getName(), e);
        }
    }


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void delete(Long id) {
    Category category = categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Category Not Found"));
    categoryRepository.delete(category);
    }


}
