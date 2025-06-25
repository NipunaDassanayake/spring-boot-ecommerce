package com.melkart_api.melkart_api.repository;

import com.melkart_api.melkart_api.model.SystemProductRequest;
import com.melkart_api.melkart_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemProductRequestRepository extends JpaRepository<SystemProductRequest , Long> {
    List<SystemProductRequest> findByRequestedBy(User user);
}
