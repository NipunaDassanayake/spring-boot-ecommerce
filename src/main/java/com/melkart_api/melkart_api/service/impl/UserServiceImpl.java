package com.melkart_api.melkart_api.service.impl;

import com.cloudinary.Cloudinary;
import com.melkart_api.melkart_api.controller.dto.request.UserCreateRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetAllUsersResponseDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetUserByIdResponseDTO;
import com.melkart_api.melkart_api.controller.dto.request.UpdateUserRequestDTO;
import com.melkart_api.melkart_api.exceptions.UserNotFoundException;
import com.melkart_api.melkart_api.model.User;
import com.melkart_api.melkart_api.repository.UserRepository;
import com.melkart_api.melkart_api.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;
    private final Cloudinary cloudinary;


    @Override
    public void createUser(UserCreateRequestDTO userCreateRequestDTO) {
        User user = new User();
        user.setFirstName(userCreateRequestDTO.getFirstName());
        user.setLastName(userCreateRequestDTO.getLastName());
        user.setEmail(userCreateRequestDTO.getEmail());
        user.setPassword(userCreateRequestDTO.getPassword());
//        user.setWalletBalance(userCreateRequestDTO.getWalletBalance() != null ? userCreateRequestDTO.getWalletBalance() : BigDecimal.ZERO);
//        user.setLoyaltyPoints(userCreateRequestDTO.getLoyaltyPoints() != null ? userCreateRequestDTO.getLoyaltyPoints() : 0);
        userRepository.save(user);
    }

    @Override
    public List<GetAllUsersResponseDTO> getAllUsers() {
        try {
            logger.info("Fetching all users from the database...");
            List<User> users = userRepository.findAll();

            List<GetAllUsersResponseDTO> userResponseList = users.stream()
                    .map(user -> {
                        GetAllUsersResponseDTO dto = new GetAllUsersResponseDTO();
                        dto.setId(user.getId());
                        dto.setEmail(user.getEmail());
                        dto.setFirstName(user.getFirstName());
                        dto.setLastName(user.getLastName());
                        dto.setWalletBalance(user.getWalletBalance());
                        dto.setLoyaltyPoints(user.getLoyaltyPoints());
                        return dto;
                    }).toList();

            logger.info("Successfully retrieved {} users.", userResponseList.size());
            return userResponseList;

        } catch (DataAccessException e) {
            logger.error("Database error occurred while fetching users: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve users due to a database error", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while fetching users: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error occurred while retrieving users", e);
        }
    }

    @Override
    public GetUserByIdResponseDTO getUserById(Long userId) {
        try {
            logger.info("Fetching user with ID: {}", userId);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

            GetUserByIdResponseDTO responseDTO = new GetUserByIdResponseDTO();
            responseDTO.setId(user.getId());
            responseDTO.setEmail(user.getEmail());
            responseDTO.setFirstName(user.getFirstName());
            responseDTO.setLastName(user.getLastName());
            responseDTO.setWalletBalance(user.getWalletBalance());
            responseDTO.setLoyaltyPoints(user.getLoyaltyPoints());
            responseDTO.setProfilePic(user.getProfilePic());

            logger.info("Successfully retrieved user with ID: {}", userId);
            return responseDTO;

        } catch (Exception e) {
            logger.error("Error retrieving user with ID: {}", userId, e);
            throw new RuntimeException("Failed to retrieve user", e);
        }
    }

    @Override
    public User updateUser(Long userId, UpdateUserRequestDTO updateUserRequestDTO) {
        try {
            logger.info("Updating user with ID: {}", userId);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

            if (updateUserRequestDTO.getFirstName() != null) {
                user.setFirstName(updateUserRequestDTO.getFirstName());
            }
            if (updateUserRequestDTO.getEmail() != null) {
                user.setEmail(updateUserRequestDTO.getEmail());
            }
            if (updateUserRequestDTO.getPassword() != null) {
                user.setPassword(updateUserRequestDTO.getPassword());
            }
            if (updateUserRequestDTO.getProfilePic() != null && !updateUserRequestDTO.getProfilePic().isEmpty()) {
                String profilePicUrl = cloudinary.uploader()
                        .upload(updateUserRequestDTO.getProfilePic().getBytes(),
                                Map.of("public_id", UUID.randomUUID().toString(),
                                        "folder", "ProfilePictures"))
                        .get("url")
                        .toString();
                user.setProfilePic(profilePicUrl);
            }

            User updatedUser = userRepository.save(user);
            logger.info("Successfully updated user with ID: {}", userId);
            return updatedUser;

        } catch (Exception e) {
            logger.error("Error updating user with ID: {}", userId, e);
            throw new RuntimeException("Failed to update user", e);
        }
    }

// change the status to deactivate
    @Override
    public void delete(Long userId) {
        try {
            logger.info("Deleting user with ID: {}", userId);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

            userRepository.delete(user);

            logger.info("Successfully deleted user with ID: {}", userId);

        } catch (Exception e) {
            logger.error("Error deleting user with ID: {}", userId, e);
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    @Override
    public void deactivateUser(Long userId) {
        try {
            logger.info("Deactivating user with ID: {}", userId);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

            user.setStatus(User.Status.DEACTIVATED);
            userRepository.save(user);
            logger.info("Successfully deactivated user with ID: {}", userId);

        } catch (UserNotFoundException e) {
            logger.error("User not found while deactivating: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error deactivating user with ID: {}", userId, e);
            throw new RuntimeException("Failed to deactivate user", e);
        }
    }

    @Override
    public void activateUser(Long userId) {
        try {
            logger.info("Activating user with ID: {}", userId);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

            user.setStatus(User.Status.ACTIVE);
            userRepository.save(user);
            logger.info("Successfully activated user with ID: {}", userId);

        } catch (UserNotFoundException e) {
            logger.error("User not found while activating: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error activating user with ID: {}", userId, e);
            throw new RuntimeException("Failed to activate user", e);
        }
    }


}
