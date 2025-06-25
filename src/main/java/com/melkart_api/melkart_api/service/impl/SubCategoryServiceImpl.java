package com.melkart_api.melkart_api.service.impl;

import com.melkart_api.melkart_api.controller.dto.request.SubCategoryCreateRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetAllSubCategoryDTO;
import com.melkart_api.melkart_api.exceptions.ResourceNotFoundException;
import com.melkart_api.melkart_api.model.Category;
import com.melkart_api.melkart_api.model.SubCategory;
import com.melkart_api.melkart_api.repository.CategoryRepository;
import com.melkart_api.melkart_api.repository.SubCategoryRepository;
import com.melkart_api.melkart_api.service.SubCategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SubCategoryServiceImpl implements SubCategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Override
    public SubCategory createSubCategory(SubCategoryCreateRequestDTO subCategoryCreateRequestDTO) {
        try {
            log.info("Attempting to create subcategory with name: '{}' under category ID: {}",
                    subCategoryCreateRequestDTO.getName(), subCategoryCreateRequestDTO.getCategoryId());


            Category category = categoryRepository.findById(subCategoryCreateRequestDTO.getCategoryId())
                    .orElseThrow(() -> {
                        String errorMessage = "Category with ID " + subCategoryCreateRequestDTO.getCategoryId() + " not found";
                        log.error(errorMessage);
                        return new RuntimeException(errorMessage);
                    });


            SubCategory subCategory = new SubCategory();
            subCategory.setName(subCategoryCreateRequestDTO.getName());
            subCategory.setCategory(category);


            subCategoryRepository.save(subCategory);

            log.info("Successfully created subcategory '{}' under category '{}'", subCategory.getName(), category.getName());

            return subCategory;
        } catch (RuntimeException e) {
            log.error("Error occurred while creating subcategory: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while creating subcategory: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error occurred while creating subcategory", e);
        }
    }


    @Override
    public List<GetAllSubCategoryDTO> getAllSubCategories() {
        try {
            List<SubCategory> subCategories = subCategoryRepository.findAll();

            return subCategories.stream().map(subCategory -> {
                GetAllSubCategoryDTO dto = new GetAllSubCategoryDTO();
                dto.setId(subCategory.getId());
                dto.setName(subCategory.getName());
                return dto;
            }).toList();

        } catch (Exception e) {
            log.error("Failed to retrieve subcategories", e);
            return Collections.emptyList();
        }
    }

    @Override
    public void delete(Long id) {
        try {
            log.info("Attempting to delete SubCategory with ID: {}", id);

            SubCategory subCategory = subCategoryRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("SubCategory with ID {} not found", id);
                        return new ResourceNotFoundException("SubCategory Not Found with ID: " + id);
                    });

            subCategoryRepository.delete(subCategory);
            log.info("Successfully deleted SubCategory with ID: {}", id);

        } catch (ResourceNotFoundException ex) {
            log.error("ResourceNotFoundException: {}", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            log.error("Unexpected error while deleting SubCategory with ID {}: {}", id, ex.getMessage(), ex);
            throw new RuntimeException("Unexpected error occurred while deleting subcategory", ex);
        }

}
    @Override
    public GetAllSubCategoryDTO getSubcategoryById(Long id) {
        try {
            log.info("Attempting to fetch subcategory with ID: {}", id);

            SubCategory subCategory = subCategoryRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("SubCategory with ID {} not found", id);
                        return new ResourceNotFoundException("SubCategory Not Found with ID: " + id);
                    });

            GetAllSubCategoryDTO dto = new GetAllSubCategoryDTO();
            dto.setId(subCategory.getId());
            dto.setName(subCategory.getName());

            log.info("Successfully fetched subcategory with ID: {}", id);
            return dto;

        } catch (ResourceNotFoundException ex) {
            log.error("ResourceNotFoundException: {}", ex.getMessage(), ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error while fetching subcategory with ID {}: {}", id, ex.getMessage(), ex);
            throw new RuntimeException("Unexpected error occurred while fetching subcategory", ex);
        }
    }
}




