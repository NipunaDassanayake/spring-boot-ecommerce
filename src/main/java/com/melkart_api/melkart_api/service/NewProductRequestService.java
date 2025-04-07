package com.melkart_api.melkart_api.service;

import com.melkart_api.melkart_api.controller.dto.request.NewProductRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetAllNewProductResponseDTO;
import com.melkart_api.melkart_api.model.NewProductRequest;
import java.util.List;

public interface NewProductRequestService {
    NewProductRequest createRequest(NewProductRequestDTO newProductRequestDTO);
    public List<GetAllNewProductResponseDTO> getAllNewProducts() ;
    public NewProductRequest getProductRequestById(Long id);
}