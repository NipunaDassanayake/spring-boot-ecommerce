package com.melkart_api.melkart_api.service.impl;

import com.cloudinary.Cloudinary;
import com.melkart_api.melkart_api.controller.dto.request.ProductRequestDTO;
import com.melkart_api.melkart_api.controller.dto.request.ProductUpdateRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.ProductResponseDTO;
import com.melkart_api.melkart_api.model.*;
import com.melkart_api.melkart_api.repository.AdminRepository;
import com.melkart_api.melkart_api.repository.ProductRepository;
import com.melkart_api.melkart_api.repository.SubCategoryRepository;
import com.melkart_api.melkart_api.service.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final Cloudinary cloudinary;

    @Override
    public Product createProduct(ProductRequestDTO productRequestDTO) {
        logger.info("Creating new product: {}", productRequestDTO.getName());

        Admin admin = adminRepository.findById(productRequestDTO.getAdminId())
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + productRequestDTO.getAdminId()));

        SubCategory subCategory = subCategoryRepository.findById(productRequestDTO.getSubCategoryId())
                .orElseThrow(() -> new RuntimeException("SubCategory not found with id: " + productRequestDTO.getSubCategoryId()));
        System.out.println(subCategory);
        List<String> imageUrls = new ArrayList<>();
        if (productRequestDTO.getImageUrls() != null && !productRequestDTO.getImageUrls().isEmpty()) {
            for (MultipartFile image : productRequestDTO.getImageUrls()) {
                try {
                    String imageUrl = cloudinary.uploader()
                            .upload(image.getBytes(), Map.of(
                                    "public_id", UUID.randomUUID().toString(),
                                    "folder", "Melkart"
                            ))
                            .get("url")
                            .toString();
                    imageUrls.add(imageUrl);
                } catch (Exception e) {
                    logger.error("Failed to upload product image", e);
                    throw new RuntimeException("Image upload failed", e);
                }
            }
        }

        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setCategory(productRequestDTO.getCategory());
        product.setBrand(productRequestDTO.getBrand());
        product.setModel(productRequestDTO.getModel());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setCurrency(productRequestDTO.getCurrency());
        product.setWebsiteUrl(productRequestDTO.getWebsiteUrl());
        product.setImageUrls(imageUrls);
        product.setSourceCountry(productRequestDTO.getSourceCountry());
        product.setStatus(Optional.ofNullable(productRequestDTO.getStatus()).orElse(Status.ACTIVE));
        product.setAdmin(admin);
        product.setSubCategory(subCategory);
        product.setCreatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);
        logger.info("Product created successfully with ID: {}", savedProduct.getId());

        return savedProduct;
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
                        productResponseDTO.setImageUrls(product.getImageUrls());
                        productResponseDTO.setSourceCountry(product.getSourceCountry());
                        productResponseDTO.setStatus(product.getStatus());
                        productResponseDTO.setSubCategoryName(product.getSubCategory().getName());
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

            SubCategory subCategory = subCategoryRepository.findById(productUpdateRequestDTO.getSubCategoryId())
                    .orElseThrow(() -> new RuntimeException("SubCategory not found with id: " + productUpdateRequestDTO.getSubCategoryId()));

            product.setName(productUpdateRequestDTO.getName());
            product.setCategory(productUpdateRequestDTO.getCategory());
            product.setBrand(productUpdateRequestDTO.getBrand());
            product.setModel(productUpdateRequestDTO.getModel());
            product.setDescription(productUpdateRequestDTO.getDescription());
            product.setPrice(productUpdateRequestDTO.getPrice());
            product.setCurrency(productUpdateRequestDTO.getCurrency());
            product.setWebsiteUrl(productUpdateRequestDTO.getWebsiteUrl());
            product.setSourceCountry(productUpdateRequestDTO.getSourceCountry());
            product.setStatus(productUpdateRequestDTO.getStatus());
            product.setSubCategory(subCategory);

            if (productUpdateRequestDTO.getImageUrls() != null && !productUpdateRequestDTO.getImageUrls().isEmpty()) {
                List<String> updatedImageUrls = new ArrayList<>();
                for (String imageUrl : productUpdateRequestDTO.getImageUrls()) {
                    String cloudinaryUrl = cloudinary.uploader()
                            .upload(imageUrl.getBytes(), Map.of("public_id", UUID.randomUUID().toString()))
                            .get("url")
                            .toString();
                    updatedImageUrls.add(cloudinaryUrl);
                }
                product.setImageUrls(updatedImageUrls);
            }

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