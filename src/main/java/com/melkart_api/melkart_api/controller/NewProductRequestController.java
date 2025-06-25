package com.melkart_api.melkart_api.controller;

import com.melkart_api.melkart_api.controller.dto.request.NewProductRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetAllNewProductResponseDTO;
import com.melkart_api.melkart_api.model.NewProductRequest;
import com.melkart_api.melkart_api.service.NewProductRequestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-requests")
@AllArgsConstructor
public class NewProductRequestController {

    private NewProductRequestService newProductRequestService;


    @PostMapping
    public ResponseEntity<String> createRequest(@Valid @RequestBody NewProductRequestDTO newProductRequestDTO) {
        NewProductRequest request = newProductRequestService.createRequest(newProductRequestDTO);
        return ResponseEntity.status(201).body("Product request created successfully!");
    }


    @GetMapping
    public ResponseEntity<List<GetAllNewProductResponseDTO>> getAllNewProducts() {
        return ResponseEntity.ok(newProductRequestService.getAllNewProducts());
    }

    @GetMapping("/{id}")
    public NewProductRequest getProductRequestById(@PathVariable Long id){
        return newProductRequestService.getProductRequestById(id);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GetAllNewProductResponseDTO>> getProductRequestByUser(@PathVariable Long userId) {
        List<GetAllNewProductResponseDTO> requests = newProductRequestService.getProductRequestByUser(userId);
        return ResponseEntity.ok(requests);
    }

}
