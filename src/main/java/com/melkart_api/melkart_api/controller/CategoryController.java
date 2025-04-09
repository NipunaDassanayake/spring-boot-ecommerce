package com.melkart_api.melkart_api.controller;


import com.melkart_api.melkart_api.controller.dto.request.CategoryCreateRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetAllCategoriesDTO;
import com.melkart_api.melkart_api.model.Category;
import com.melkart_api.melkart_api.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {


    private CategoryService categoryService;


    @GetMapping
    public ResponseEntity<List<GetAllCategoriesDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryCreateRequestDTO categoryCreateRequestDTO){
        System.out.println(categoryCreateRequestDTO.getName());
        Category createdCategory = categoryService.createCategory(categoryCreateRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        categoryService.delete(id);
    }



}
