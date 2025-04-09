package com.melkart_api.melkart_api.service;

import com.melkart_api.melkart_api.controller.dto.request.CreateSystemProductRequestDTO;
import com.melkart_api.melkart_api.controller.dto.request.UpdateSystemProductRequestDTO;
import com.melkart_api.melkart_api.model.SystemProductRequest;

import java.util.List;

public interface SystemProductRequestService {
    public SystemProductRequest createRequest(CreateSystemProductRequestDTO createSystemProductRequestDTO);
    public SystemProductRequest updateRequest(Long id, UpdateSystemProductRequestDTO dto);
    public List<SystemProductRequest> getAllRequests();
    public SystemProductRequest getRequestById(Long id);



}
