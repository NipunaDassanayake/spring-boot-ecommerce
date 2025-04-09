package com.melkart_api.melkart_api.service;


import com.melkart_api.melkart_api.controller.dto.request.UserCreateRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetAllUsersResponseDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetUserByIdResponseDTO;
import com.melkart_api.melkart_api.controller.dto.request.UpdateUserRequestDTO;
import com.melkart_api.melkart_api.model.User;

import java.util.List;

public interface UserService {
    void createUser(UserCreateRequestDTO userDTO);
    public GetUserByIdResponseDTO getUserById(Long userId);
    List<GetAllUsersResponseDTO> getAllUsers();
    public void delete(Long userId);
    User updateUser(Long userId, UpdateUserRequestDTO updateUserRequestDTO);
}
