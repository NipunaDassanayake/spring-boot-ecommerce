package com.melkart_api.melkart_api.service.impl;


import com.melkart_api.melkart_api.dto.CreateSystemProductRequestDTO;
import com.melkart_api.melkart_api.dto.UpdateSystemProductRequestDTO;
import com.melkart_api.melkart_api.model.*;
import com.melkart_api.melkart_api.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SystemProductRequestService {

    private final SystemProductRequestRepository requestRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public SystemProductRequest createRequest(CreateSystemProductRequestDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        SystemProductRequest request = new SystemProductRequest();
        request.setProduct(product);
        request.setRequestedBy(user);
        request.setExpectedPrice(dto.getExpectedPrice());
        request.setSourceCountry(dto.getSourceCountry());
        request.setDestinationCountry(dto.getDestinationCountry());
        request.setDestinationCity(dto.getDestinationCity());
        request.setWithBox(dto.getWithBox());
        request.setExpectedDeliveryDate(dto.getExpectedDeliveryDate());
        request.setQuantity(dto.getQuantity());
        request.setStatus(RequestStatus.PENDING); // default status

        return requestRepository.save(request);
    }

    public SystemProductRequest updateRequest(Long id, UpdateSystemProductRequestDTO dto) {
        SystemProductRequest request = requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Request not found"));

        if (dto.getExpectedPrice() != null) {
            request.setExpectedPrice(dto.getExpectedPrice());
        }
        if (dto.getSourceCountry() != null) {
            request.setSourceCountry(dto.getSourceCountry());
        }
        if (dto.getDestinationCountry() != null) {
            request.setDestinationCountry(dto.getDestinationCountry());
        }
        if (dto.getDestinationCity() != null) {
            request.setDestinationCity(dto.getDestinationCity());
        }
        if (dto.getWithBox() != null) {
            request.setWithBox(dto.getWithBox());
        }
        if (dto.getExpectedDeliveryDate() != null) {
            request.setExpectedDeliveryDate(dto.getExpectedDeliveryDate());
        }
        if (dto.getQuantity() != null) {
            request.setQuantity(dto.getQuantity());
        }
        if (dto.getStatus() != null) {
            request.setStatus(dto.getStatus());
        }

        return requestRepository.save(request);
    }

    public List<SystemProductRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    public SystemProductRequest getRequestById(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Request not found"));
    }

    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }
}

