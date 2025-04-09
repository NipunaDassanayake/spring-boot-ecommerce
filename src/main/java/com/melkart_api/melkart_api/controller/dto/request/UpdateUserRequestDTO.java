package com.melkart_api.melkart_api.controller.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class UpdateUserRequestDTO {

    private String firstName;
    private String lastName;
    private String email;
    private MultipartFile profilePic;
    private String password;

}
