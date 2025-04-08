package com.melkart_api.melkart_api.service;

import com.melkart_api.melkart_api.controller.dto.request.SubCategoryRequestDTO;
import com.melkart_api.melkart_api.model.SubCategory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SubCategoryService {

    public SubCategory createSubCategory(SubCategoryRequestDTO subCategoryRequestDTO);
    public List<SubCategory> getAllSubCategories();
    void delete(Long id);

}
