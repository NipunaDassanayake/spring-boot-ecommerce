package com.melkart_api.melkart_api.service.impl;

import com.melkart_api.melkart_api.controller.dto.request.NewProductRequestDTO;
import com.melkart_api.melkart_api.model.NewProductRequest;
import com.melkart_api.melkart_api.model.RequestStatus;
import com.melkart_api.melkart_api.model.User;
import com.melkart_api.melkart_api.repository.NewProductRequestRepository;
import com.melkart_api.melkart_api.repository.UserRepository;
import com.melkart_api.melkart_api.service.NewProductRequestService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NewProductRequestServiceImpl implements NewProductRequestService {

    private static final Logger logger = LoggerFactory.getLogger(NewProductRequestServiceImpl.class);
    private final NewProductRequestRepository newProductRequestRepository;
    private final UserRepository userRepository;

    @Override
    public NewProductRequest createRequest(NewProductRequestDTO newProductRequestDTO) {
        try {
            User user = userRepository.findById(newProductRequestDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + newProductRequestDTO.getUserId()));

            NewProductRequest request = new NewProductRequest();
            request.setName(newProductRequestDTO.getName());
            request.setCategory(newProductRequestDTO.getCategory());
            request.setDescription(newProductRequestDTO.getDescription());
            request.setExpectedPrice(newProductRequestDTO.getExpectedPrice());
            request.setCurrency(newProductRequestDTO.getCurrency());
            request.setSourceCountry(newProductRequestDTO.getSourceCountry());
            request.setRequestedBy(user);
            request.setStatus(RequestStatus.PENDING);

            return newProductRequestRepository.save(request);

        } catch (DataAccessException e) {
            logger.error("Failed to create new product request due to database error: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create new product request due to database error", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while creating new product request: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error occurred while creating new product request", e);
        }
    }

    @Override
    public List<NewProductRequest> getAllRequests() {
        try {
            return newProductRequestRepository.findAll();
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve all new product requests due to database error: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve all new product requests due to database error", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while retrieving all new product requests: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error occurred while retrieving all new product requests", e);
        }
    }
}
