package com.melkart_api.melkart_api.controller;

import com.melkart_api.melkart_api.controller.dto.request.ProductRequestDTO;
import com.melkart_api.melkart_api.model.Product;
import com.melkart_api.melkart_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@ModelAttribute ProductRequestDTO productRequestDTO) {
        Product createdProduct = productService.createProduct(productRequestDTO);
        return ResponseEntity.ok(createdProduct);
    }
}
