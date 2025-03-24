package com.melkart_api.melkart_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @OneToMany(mappedBy = "requestedBy", cascade = CascadeType.ALL)
    private List<NewProductRequest> productRequests;

    private BigDecimal walletBalance = BigDecimal.ZERO;
    private Integer loyaltyPoints = 0;
}
