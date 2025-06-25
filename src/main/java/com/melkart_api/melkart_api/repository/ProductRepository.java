package com.melkart_api.melkart_api.repository;

import com.melkart_api.melkart_api.model.Product;
import com.melkart_api.melkart_api.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySubCategory(SubCategory subCategory);
}