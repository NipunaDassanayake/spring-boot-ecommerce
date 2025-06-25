package com.melkart_api.melkart_api.controller;

import com.melkart_api.melkart_api.controller.dto.request.ProductCreateRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.ProductResponseDTO;
import com.melkart_api.melkart_api.model.Product;
import com.melkart_api.melkart_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@ModelAttribute ProductCreateRequestDTO productCreateRequestDTO) {
        Product createdProduct = productService.createProduct(productCreateRequestDTO);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/subcategory/{subCategoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsBySubcategory(@PathVariable Long subCategoryId) {
        List<ProductResponseDTO> products = productService.getProductsBySubcategory(subCategoryId);
        return ResponseEntity.ok(products);
    }

}
