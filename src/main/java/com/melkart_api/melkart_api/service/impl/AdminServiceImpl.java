package com.melkart_api.melkart_api.service.impl;

import com.melkart_api.melkart_api.controller.dto.request.AdminCreateRequestDTO;
import com.melkart_api.melkart_api.controller.dto.request.AdminUpdateRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetAdminByIdDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetAllAdminsDTO;
import com.melkart_api.melkart_api.exceptions.AdminNotFoundException;
import com.melkart_api.melkart_api.model.Admin;
import com.melkart_api.melkart_api.repository.AdminRepository;
import com.melkart_api.melkart_api.service.AdminService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private final AdminRepository adminRepository;

    @Override
    public Admin createAdmin(AdminCreateRequestDTO adminCreateRequestDTO) {

        try {
            Admin admin = new Admin();
            admin.setName(adminCreateRequestDTO.getName());
            admin.setEmail(adminCreateRequestDTO.getEmail());
            admin.setPassword(adminCreateRequestDTO.getPassword()); // In production, hash the password before saving
            return adminRepository.save(admin);
        } catch (DataAccessException e) {
            logger.error("Failed to create admin due to database error: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create admin due to database error", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while creating admin: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error occurred while creating admin", e);
        }
    }

    @Override
    public List<GetAllAdminsDTO> getAllAdmins() {
        try {
            List<Admin> admins = adminRepository.findAll();
            return admins.stream()
                    .map(admin -> {
                        GetAllAdminsDTO getAllAdminsDTO = new GetAllAdminsDTO();
                        getAllAdminsDTO.setId(admin.getId());
                        getAllAdminsDTO.setEmail(admin.getEmail());
                        getAllAdminsDTO.setName(admin.getName());

                        return getAllAdminsDTO;
                    })
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve admins due to database error: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve admins due to database error", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while retrieving admins: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error occurred while retrieving admins", e);
        }
    }

    @Override
    public Admin updateAdmin(Long id, AdminUpdateRequestDTO adminUpdateRequestDTO) {

        try {
            Admin admin = adminRepository.findById(id)
                    .orElseThrow(() -> new AdminNotFoundException("Admin not found with id: " + id));
            admin.setName(adminUpdateRequestDTO.getName());
            admin.setEmail(adminUpdateRequestDTO.getEmail());
            admin.setPassword(adminUpdateRequestDTO.getPassword()); // In production, hash the password before saving
            return adminRepository.save(admin);
        } catch (DataAccessException e) {
            logger.error("Failed to update admin due to database error: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update admin due to database error", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while updating admin: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error occurred while updating admin", e);
        }
    }

    @Override
    public void deleteAdmin(Long id) {
        try {
            Admin admin = adminRepository.findById(id)
                    .orElseThrow(() -> new AdminNotFoundException("Admin not found with id: " + id));
            adminRepository.deleteById(id);
        } catch (DataAccessException e) {
            logger.error("Failed to delete admin due to database error: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to delete admin due to database error", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while deleting admin: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error occurred while deleting admin", e);
        }
    }

    @Override
    public GetAdminByIdDTO getAdminById(Long id) {
        try {
           Admin admin =  adminRepository.findById(id)
                    .orElseThrow(() -> new AdminNotFoundException("Admin not found with id: " + id));
            GetAdminByIdDTO getAdminByIdDTO = new GetAdminByIdDTO();
            getAdminByIdDTO.setId(admin.getId());
            getAdminByIdDTO.setEmail(admin.getEmail());
            getAdminByIdDTO.setName(admin.getName());
            return getAdminByIdDTO;

        } catch (DataAccessException e) {
            logger.error("Failed to retrieve admin by id due to database error: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve admin by id due to database error", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while retrieving admin by id: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error occurred while retrieving admin by id", e);
        }
    }


}
