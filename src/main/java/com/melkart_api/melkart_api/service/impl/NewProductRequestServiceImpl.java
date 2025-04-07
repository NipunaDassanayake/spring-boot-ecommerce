package com.melkart_api.melkart_api.service.impl;

import com.melkart_api.melkart_api.controller.dto.request.NewProductRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetAllNewProductResponseDTO;
import com.melkart_api.melkart_api.model.NewProductRequest;
import com.melkart_api.melkart_api.model.RequestStatus;
import com.melkart_api.melkart_api.model.User;
import com.melkart_api.melkart_api.repository.NewProductRequestRepository;
import com.melkart_api.melkart_api.repository.UserRepository;
import com.melkart_api.melkart_api.service.NewProductRequestService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NewProductRequestServiceImpl implements NewProductRequestService {


    private final NewProductRequestRepository newProductRequestRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(NewProductRequestService.class);

    @Transactional
    public NewProductRequest createRequest(NewProductRequestDTO newProductRequestDTO) {
        logger.info("Creating new product request for user with ID: {}", newProductRequestDTO.getUserId());

        try {

            User user = userRepository.findById(newProductRequestDTO.getUserId())
                    .orElseThrow(() -> {
                        logger.error("User not found with ID: {}", newProductRequestDTO.getUserId());
                        return new EntityNotFoundException("User not found with id: " + newProductRequestDTO.getUserId());
                    });

            logger.info("User found: {}", user.getId());

            NewProductRequest request = new NewProductRequest();
            request.setName(newProductRequestDTO.getName());
            request.setDescription(newProductRequestDTO.getDescription());
            request.setExpectedPrice(newProductRequestDTO.getExpectedPrice());
            request.setCurrency(newProductRequestDTO.getCurrency());
            request.setSourceCountry(newProductRequestDTO.getSourceCountry());
            request.setDestinationCountry(newProductRequestDTO.getDestinationCountry());
            request.setDestinationCity(newProductRequestDTO.getDestinationCity());
            request.setWebsiteUrl(newProductRequestDTO.getWebsiteUrl());
            request.setQuantity(newProductRequestDTO.getQuantity());
            request.setWithBox(newProductRequestDTO.getWithBox());
            request.setExpectedDeliveryDate(newProductRequestDTO.getExpectedDeliveryDate());
            request.setRequestedBy(user);
            request.setStatus(RequestStatus.PENDING);

            logger.info("New product request created with name: {}", request.getName());


            NewProductRequest savedRequest = newProductRequestRepository.save(request);
            logger.info("New product request saved with ID: {}", savedRequest.getId());

            return savedRequest;

        } catch (DataAccessException e) {
            logger.error("Database error while creating new product request", e);
            throw new RuntimeException("Database error while creating new product request", e);
        } catch (Exception e) {
            logger.error("Unexpected error while creating new product request", e);
            throw new RuntimeException("Unexpected error while creating new product request", e);
        }
    }


    @Override
    public List<GetAllNewProductResponseDTO> getAllNewProducts() {
        try {
            List<NewProductRequest> requests = newProductRequestRepository.findAll();
            return requests.stream()
                    .map(request -> {
                        GetAllNewProductResponseDTO responseDTO = new GetAllNewProductResponseDTO();
                        responseDTO.setId(request.getId());
                        responseDTO.setName(request.getName());
                        responseDTO.setDescription(request.getDescription());
                        responseDTO.setExpectedPrice(request.getExpectedPrice());
                        responseDTO.setCurrency(request.getCurrency());
                        responseDTO.setSourceCountry(request.getSourceCountry());
                        responseDTO.setDestinationCountry(request.getDestinationCountry());
                        responseDTO.setDestinationCity(request.getDestinationCity());
                        responseDTO.setWebsiteUrl(request.getWebsiteUrl());
                        responseDTO.setQuantity(request.getQuantity());
                        responseDTO.setWithBox(request.getWithBox());
                        responseDTO.setCreatedAt(request.getCreatedAt());
                        responseDTO.setExpectedDeliveryDate(request.getExpectedDeliveryDate());
                        responseDTO.setStatus(request.getStatus());
                        responseDTO.setUserId(request.getRequestedBy().getId());

                        return responseDTO;
                    })
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve all new products due to database error: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve all new products due to database error", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while retrieving all new products: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error occurred while retrieving all new products", e);
        }
    }


    @Override
    public NewProductRequest getProductRequestById(Long id) {
        try {
            return newProductRequestRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("New product request not found with id: " + id));
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve new product request with id {} due to database error: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve new product request due to database error", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while retrieving new product request with id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Unexpected error occurred while retrieving new product request", e);
        }
    }



}
