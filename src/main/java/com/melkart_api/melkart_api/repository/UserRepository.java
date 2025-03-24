package com.melkart_api.melkart_api.repository;

import com.melkart_api.melkart_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
