package com.melkart_api.melkart_api.service;

import com.melkart_api.melkart_api.controller.dto.request.ProductCreateRequestDTO;
import com.melkart_api.melkart_api.controller.dto.request.ProductUpdateRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.ProductResponseDTO;
import com.melkart_api.melkart_api.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductCreateRequestDTO productCreateRequestDTO);
    List<ProductResponseDTO> getAllProducts();
    Product updateProduct(Long id, ProductUpdateRequestDTO productUpdateRequestDTO);
    void deleteProduct(Long id);
    ProductResponseDTO getProductById(Long id);
    List<ProductResponseDTO> getProductsBySubcategory(Long subCategoryId);

    }

