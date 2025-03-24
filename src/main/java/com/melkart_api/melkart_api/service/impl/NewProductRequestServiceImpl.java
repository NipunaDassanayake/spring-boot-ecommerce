package com.melkart_api.melkart_api.service.impl;

import com.melkart_api.melkart_api.controller.dto.request.NewProductRequestDTO;
import com.melkart_api.melkart_api.model.NewProductRequest;
import com.melkart_api.melkart_api.model.RequestStatus;
import com.melkart_api.melkart_api.model.User;
import com.melkart_api.melkart_api.repository.NewProductRequestRepository;
import com.melkart_api.melkart_api.repository.UserRepository;
import com.melkart_api.melkart_api.service.NewProductRequestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class NewProductRequestServiceImpl implements NewProductRequestService {

    private final NewProductRequestRepository newProductRequestRepository;
    private final UserRepository userRepository;

    @Override
    public NewProductRequest createRequest(NewProductRequestDTO newProductRequestDTO) {
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
    }

    @Override
    public List<NewProductRequest> getAllRequests() {
        return newProductRequestRepository.findAll();
    }
}