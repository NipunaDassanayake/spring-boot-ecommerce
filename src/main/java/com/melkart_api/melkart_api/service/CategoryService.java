package com.melkart_api.melkart_api.service;

import com.melkart_api.melkart_api.controller.dto.request.CategoryRequestDTO;
import com.melkart_api.melkart_api.model.Category;

import java.util.List;

public interface CategoryService {
    public Category createCategory(CategoryRequestDTO categoryRequestDTO);
    public List<Category> getAllCategories();
    void delete(Long id);
}
