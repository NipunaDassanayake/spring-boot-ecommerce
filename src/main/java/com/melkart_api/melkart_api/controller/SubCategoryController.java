package com.melkart_api.melkart_api.controller;


import com.melkart_api.melkart_api.controller.dto.request.SubCategoryRequestDTO;
import com.melkart_api.melkart_api.model.Category;
import com.melkart_api.melkart_api.model.SubCategory;
import com.melkart_api.melkart_api.service.SubCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subcategories")
public class SubCategoryController {


    private SubCategoryService subCategoryService;


    @PostMapping
    public ResponseEntity<SubCategory> createSubCategory(@RequestBody SubCategoryRequestDTO subCategoryRequestDTO) {
        System.out.println(subCategoryRequestDTO.getName());
        System.out.println(subCategoryRequestDTO.getCategoryId());
        SubCategory subCategory = subCategoryService.createSubCategory(subCategoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(subCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        subCategoryService.delete(id);
        return ResponseEntity.ok("SubCategory deleted successfully.");
    }

    @GetMapping
    public ResponseEntity<List<SubCategory>> getAllSubCategories(){
        return ResponseEntity.ok(subCategoryService.getAllSubCategories());
    }


}
