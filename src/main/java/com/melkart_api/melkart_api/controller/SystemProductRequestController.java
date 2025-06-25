package com.melkart_api.melkart_api.controller;

import com.melkart_api.melkart_api.controller.dto.request.CreateSystemProductRequestDTO;
import com.melkart_api.melkart_api.controller.dto.request.UpdateSystemProductRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetSystemProductRequestByIdDTO;
import com.melkart_api.melkart_api.controller.dto.response.SystemProductResponseDTO;
import com.melkart_api.melkart_api.model.SystemProductRequest;
import com.melkart_api.melkart_api.service.SystemProductRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/system-product-requests")
@RequiredArgsConstructor
public class SystemProductRequestController {

    private final SystemProductRequestService requestService;

    @PostMapping
    public ResponseEntity<SystemProductRequest> createRequest(@RequestBody CreateSystemProductRequestDTO dto) {
        return ResponseEntity.ok(requestService.createRequest(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemProductRequest> updateRequest(@PathVariable Long id, @RequestBody UpdateSystemProductRequestDTO dto) {
        return ResponseEntity.ok(requestService.updateRequest(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<SystemProductResponseDTO>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSystemProductRequestByIdDTO> getRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(requestService.getRequestById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        requestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SystemProductResponseDTO>> getRequestsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(requestService.getRequestsByUser(userId));
    }
}

