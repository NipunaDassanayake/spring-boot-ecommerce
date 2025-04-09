package com.melkart_api.melkart_api.service;

import com.melkart_api.melkart_api.controller.dto.request.CategoryCreateRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetAllCategoriesDTO;
import com.melkart_api.melkart_api.model.Category;

import java.util.List;

public interface CategoryService {
    public Category createCategory(CategoryCreateRequestDTO categoryCreateRequestDTO);
//    public List<Category> getAllCategories();
    void delete(Long id);
    public List<GetAllCategoriesDTO> getAllCategories();
}
