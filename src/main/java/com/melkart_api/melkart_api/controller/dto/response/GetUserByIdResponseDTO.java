package com.melkart_api.melkart_api.controller.dto.response;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class GetUserByIdResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePic;
    private BigDecimal walletBalance;
    private Integer loyaltyPoints;

}
