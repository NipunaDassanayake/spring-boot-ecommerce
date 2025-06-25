package com.melkart_api.melkart_api.service;

import com.melkart_api.melkart_api.controller.dto.request.SubCategoryCreateRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetAllSubCategoryDTO;
import com.melkart_api.melkart_api.model.SubCategory;

import java.util.List;

public interface SubCategoryService {

    public SubCategory createSubCategory(SubCategoryCreateRequestDTO subCategoryCreateRequestDTO);
    public List<GetAllSubCategoryDTO> getAllSubCategories();
    void delete(Long id);
    GetAllSubCategoryDTO getSubcategoryById(Long id);
}
