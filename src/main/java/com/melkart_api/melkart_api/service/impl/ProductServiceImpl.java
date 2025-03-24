package com.melkart_api.melkart_api.service.impl;

import com.melkart_api.melkart_api.controller.dto.request.ProductRequestDTO;
import com.melkart_api.melkart_api.controller.dto.request.ProductUpdateRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.ProductResponseDTO;
import com.melkart_api.melkart_api.model.Admin;
import com.melkart_api.melkart_api.model.Product;
import com.melkart_api.melkart_api.repository.AdminRepository;
import com.melkart_api.melkart_api.repository.ProductRepository;
import com.melkart_api.melkart_api.service.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    @Override
    public Product createProduct(ProductRequestDTO productRequestDTO) {
        try {
            Admin admin = adminRepository.findById(productRequestDTO.getAdminId())
                    .orElseThrow(() -> new RuntimeException("Admin not found with id: " + productRequestDTO.getAdminId()));

            Product product = new Product();
            product.setName(productRequestDTO.getName());
            product.setCategory(productRequestDTO.getCategory());
            product.setBrand(productRequestDTO.getBrand());
            product.setModel(productRequestDTO.getModel());
            product.setDescription(productRequestDTO.getDescription());
            product.setPrice(productRequestDTO.getPrice());
            product.setCurrency(productRequestDTO.getCurrency());
            product.setWebsiteUrl(productRequestDTO.getWebsiteUrl());
            product.setImageUrl(productRequestDTO.getImageUrl());
            product.setSourceCountry(productRequestDTO.getSourceCountry());
            product.setStatus(productRequestDTO.getStatus());
            product.setAdmin(admin);
            product.setCreatedAt(productRequestDTO.getCreatedAt());

            return productRepository.save(product);

        } catch (DataAccessException e) {
            logger.error("Failed to create product due to database error: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create product due to database error", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while creating product: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error occurred while creating product", e);
        }
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();
            return products.stream()
                    .map(product -> {
                        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
                        productResponseDTO.setName(product.getName());
                        productResponseDTO.setCategory(product.getCategory());
                        productResponseDTO.setBrand(product.getBrand());
                        productResponseDTO.setModel(product.getModel());
                        productResponseDTO.setDescription(product.getDescription());
                        productResponseDTO.setPrice(product.getPrice());
                        productResponseDTO.setCurrency(product.getCurrency());
                        productResponseDTO.setWebsiteUrl(product.getWebsiteUrl());
                        productResponseDTO.setImageUrl(product.getImageUrl());
                        productResponseDTO.setSourceCountry(product.getSourceCountry());
                        productResponseDTO.setStatus(product.getStatus());

                        return productResponseDTO;
                    })
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve all products due to database error: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve all products due to database error", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while retrieving all products: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error occurred while retrieving all products", e);
        }
    }

    @Override
    public Product updateProduct(Long id, ProductUpdateRequestDTO productUpdateRequestDTO) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

            product.setName(productUpdateRequestDTO.getName());
            product.setCategory(productUpdateRequestDTO.getCategory());
            product.setBrand(productUpdateRequestDTO.getBrand());
            product.setModel(productUpdateRequestDTO.getModel());
            product.setDescription(productUpdateRequestDTO.getDescription());
            product.setPrice(productUpdateRequestDTO.getPrice());
            product.setCurrency(productUpdateRequestDTO.getCurrency());
            product.setWebsiteUrl(productUpdateRequestDTO.getWebsiteUrl());
            product.setImageUrl(productUpdateRequestDTO.getImageUrl());
            product.setSourceCountry(productUpdateRequestDTO.getSourceCountry());
            product.setStatus(productUpdateRequestDTO.getStatus());

            return productRepository.save(product);

        } catch (DataAccessException e) {
            logger.error("Failed to update product with id {} due to database error: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to update product due to database error", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while updating product with id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Unexpected error occurred while updating product", e);
        }
    }

    @Override
    public void deleteProduct(Long id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
            productRepository.deleteById(id);
        } catch (DataAccessException e) {
            logger.error("Failed to delete product with id {} due to database error: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete product due to database error", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while deleting product with id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Unexpected error occurred while deleting product", e);
        }
    }
}
