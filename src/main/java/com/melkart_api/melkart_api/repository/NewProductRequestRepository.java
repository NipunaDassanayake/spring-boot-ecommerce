package com.melkart_api.melkart_api.repository;

import com.melkart_api.melkart_api.model.NewProductRequest;
import com.melkart_api.melkart_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewProductRequestRepository extends JpaRepository<NewProductRequest, Long> {
    List<NewProductRequest> findByRequestedBy(User user);
}
