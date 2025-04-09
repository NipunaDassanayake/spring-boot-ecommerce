package com.melkart_api.melkart_api.service.impl;

import com.melkart_api.melkart_api.controller.dto.request.CategoryCreateRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetAllCategoriesDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetAllSubCategoryDTO;
import com.melkart_api.melkart_api.model.Category;
import com.melkart_api.melkart_api.repository.CategoryRepository;
import com.melkart_api.melkart_api.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    @Override
    public Category createCategory(CategoryCreateRequestDTO categoryCreateRequestDTO) {
        try {
            log.info("Attempting to create category with name: {}", categoryCreateRequestDTO.getName());

            Category category = new Category();
            category.setName(categoryCreateRequestDTO.getName());

            categoryRepository.save(category);

            log.info("Successfully created category: {}", category.getName());

            return category;
        } catch (Exception e) {
            log.error("Error occurred while creating category with name: {}. Error: {}", categoryCreateRequestDTO.getName(), e.getMessage(), e);
            throw new RuntimeException("Error occurred while creating category: " + categoryCreateRequestDTO.getName(), e);
        }
    }



    @Override
    public void delete(Long id) {
    Category category = categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Category Not Found"));
    categoryRepository.delete(category);
    }

    @Override
    public List<GetAllCategoriesDTO> getAllCategories() {
        try {
            List<Category> categories = categoryRepository.findAll();
            log.info("Fetched {} categories from database", categories.size());

            return categories.stream().map(category -> {
                GetAllCategoriesDTO dto = new GetAllCategoriesDTO();
                dto.setId(category.getId());
                dto.setName(category.getName());

                List<GetAllSubCategoryDTO> subCategoryDTOs = category.getSubCategories()
                        .stream()
                        .map(subCategory -> {
                            GetAllSubCategoryDTO subDTO = new GetAllSubCategoryDTO();
                            subDTO.setId(subCategory.getId());
                            subDTO.setName(subCategory.getName());
                            return subDTO;
                        }).toList();

                dto.setSubCategories(subCategoryDTOs);

                return dto;
            }).toList();

        } catch (Exception e) {
            log.error("Error occurred while fetching categories: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }



}
