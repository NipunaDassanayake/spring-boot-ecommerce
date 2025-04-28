package com.melkart_api.melkart_api.service.impl;
import com.melkart_api.melkart_api.controller.dto.request.CreateSystemProductRequestDTO;
import com.melkart_api.melkart_api.controller.dto.request.UpdateSystemProductRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetSystemProductRequestByIdDTO;
import com.melkart_api.melkart_api.controller.dto.response.SystemProductResponseDTO;
import com.melkart_api.melkart_api.model.Product;
import com.melkart_api.melkart_api.model.SystemProductRequest;
import com.melkart_api.melkart_api.model.User;
import com.melkart_api.melkart_api.repository.ProductRepository;
import com.melkart_api.melkart_api.repository.SystemProductRequestRepository;
import com.melkart_api.melkart_api.repository.UserRepository;
import com.melkart_api.melkart_api.service.SystemProductRequestService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SystemProductRequestServiceImpl implements SystemProductRequestService {

    private final SystemProductRequestRepository requestRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public SystemProductRequest createRequest(CreateSystemProductRequestDTO dto) {
        try {
            log.info("Creating system product request with productId: {} and userId: {}", dto.getProductId(), dto.getUserId());

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

            SystemProductRequest savedRequest = requestRepository.save(request);
            log.info("System product request created successfully with id: {}", savedRequest.getId());
            return savedRequest;
        } catch (Exception e) {
            log.error("Error occurred while creating system product request: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public SystemProductRequest updateRequest(Long id, UpdateSystemProductRequestDTO dto) {
        try {
            log.info("Updating system product request with id: {}", id);

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

            SystemProductRequest updatedRequest = requestRepository.save(request);
            log.info("System product request updated successfully with id: {}", updatedRequest.getId());
            return updatedRequest;
        } catch (Exception e) {
            log.error("Error occurred while updating system product request with id {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<SystemProductResponseDTO> getAllRequests() {
        try {
            log.info("Fetching all system product requests");

            List<SystemProductRequest> requests = requestRepository.findAll();
            List<SystemProductResponseDTO> responseDTOs = requests.stream()
                    .map(request -> {
                        SystemProductResponseDTO dto = new SystemProductResponseDTO();
                        dto.setId(request.getId());
                        dto.setExpectedPrice(request.getExpectedPrice());
                        dto.setSourceCountry(request.getSourceCountry());
                        dto.setDestinationCountry(request.getDestinationCountry());
                        dto.setDestinationCity(request.getDestinationCity());
                        dto.setWithBox(request.getWithBox());
                        dto.setExpectedDeliveryDate(request.getExpectedDeliveryDate());
                        dto.setQuantity(request.getQuantity());
                        dto.setCreatedAt(request.getCreatedAt());
                        dto.setStatus(request.getStatus());
                        dto.setProductId(request.getProduct().getId());
                        dto.setUserId(request.getRequestedBy().getId());
                        return dto;
                    })
                    .collect(Collectors.toList());

            log.info("Fetched {} system product requests", responseDTOs.size());
            return responseDTOs;
        } catch (Exception e) {
            log.error("Error occurred while fetching all system product requests: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public GetSystemProductRequestByIdDTO getRequestById(Long id) {
        try {
            log.info("Fetching system product request with id: {}", id);

            SystemProductRequest request = requestRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Request not found"));

            GetSystemProductRequestByIdDTO dto = new GetSystemProductRequestByIdDTO();
            dto.setId(request.getId());
            dto.setExpectedPrice(request.getExpectedPrice());
            dto.setSourceCountry(request.getSourceCountry());
            dto.setDestinationCountry(request.getDestinationCountry());
            dto.setDestinationCity(request.getDestinationCity());
            dto.setWithBox(request.getWithBox());
            dto.setExpectedDeliveryDate(request.getExpectedDeliveryDate());
            dto.setQuantity(request.getQuantity());
            dto.setCreatedAt(request.getCreatedAt());
            dto.setStatus(request.getStatus());
            dto.setProductId(request.getProduct().getId());
            dto.setUserId(request.getRequestedBy().getId());

            log.info("Fetched system product request with id: {}", id);
            return dto;
        } catch (Exception e) {
            log.error("Error occurred while fetching system product request with id {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteRequest(Long id) {
        try {
            log.info("Deleting system product request with id: {}", id);
            requestRepository.deleteById(id);
            log.info("System product request with id: {} deleted successfully", id);
        } catch (Exception e) {
            log.error("Error occurred while deleting system product request with id {}: {}", id, e.getMessage());
            throw e;
        }
    }
}
