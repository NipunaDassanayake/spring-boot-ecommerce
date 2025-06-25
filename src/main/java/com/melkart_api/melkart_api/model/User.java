package com.melkart_api.melkart_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public enum Status {
        ACTIVE,
        DEACTIVATED
    }

    private String profilePic;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "requestedBy", cascade = CascadeType.ALL)
    private List<NewProductRequest> productRequests;

    private BigDecimal walletBalance = BigDecimal.ZERO;
    private Integer loyaltyPoints = 0;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE; // Default to ACTIVE
}
