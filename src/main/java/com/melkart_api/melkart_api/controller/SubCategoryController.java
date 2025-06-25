package com.melkart_api.melkart_api.controller;


import com.melkart_api.melkart_api.controller.dto.request.SubCategoryCreateRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetAllSubCategoryDTO;
import com.melkart_api.melkart_api.model.SubCategory;
import com.melkart_api.melkart_api.service.SubCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subcategories")
public class SubCategoryController {


    private SubCategoryService subCategoryService;


    @PostMapping
    public ResponseEntity<SubCategory> createSubCategory(@RequestBody SubCategoryCreateRequestDTO subCategoryCreateRequestDTO) {
        System.out.println(subCategoryCreateRequestDTO.getName());
        System.out.println(subCategoryCreateRequestDTO.getCategoryId());
        SubCategory subCategory = subCategoryService.createSubCategory(subCategoryCreateRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(subCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        subCategoryService.delete(id);
        return ResponseEntity.ok("SubCategory deleted successfully.");
    }

    @GetMapping
    public ResponseEntity<List<GetAllSubCategoryDTO>> getAllSubCategories() {
        return ResponseEntity.ok(subCategoryService.getAllSubCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetAllSubCategoryDTO> getSubcategoryById(@PathVariable Long id) {
        GetAllSubCategoryDTO subCategory = subCategoryService.getSubcategoryById(id);
        return ResponseEntity.ok(subCategory);
    }


}
