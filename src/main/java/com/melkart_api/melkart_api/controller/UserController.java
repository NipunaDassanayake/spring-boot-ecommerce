package com.melkart_api.melkart_api.controller;

import com.melkart_api.melkart_api.controller.dto.request.UserRequestDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetAllUsersResponseDTO;
import com.melkart_api.melkart_api.controller.dto.response.GetUserByIdResponseDTO;
import com.melkart_api.melkart_api.controller.dto.request.UpdateUserRequestDTO;
import com.melkart_api.melkart_api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private  UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        userService.createUser(userRequestDTO);
        return ResponseEntity.ok("User Created Successfully");
    }


    @GetMapping
    public ResponseEntity<List<GetAllUsersResponseDTO>> getAllUsers() {
        List<GetAllUsersResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<GetUserByIdResponseDTO> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> delete(@PathVariable Long userId){
        userService.delete(userId);
        return ResponseEntity.status(200).body("User deleted Successfullly");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId,@ModelAttribute  UpdateUserRequestDTO updateUserRequestDTO)
    {
        System.out.println(userId);
        System.out.println(updateUserRequestDTO);
        userService.updateUser(userId,updateUserRequestDTO);
        return ResponseEntity.ok("User updated successfully");
    }
}
