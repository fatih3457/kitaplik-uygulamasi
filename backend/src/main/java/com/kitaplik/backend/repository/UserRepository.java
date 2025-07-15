package com.kitaplik.backend.repository;

import com.kitaplik.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA, metot isminden sorguyu otomatik oluşturur:
    // "SELECT u FROM User u WHERE u.username = :username"
    Optional<User> findByUsername(String username);
}