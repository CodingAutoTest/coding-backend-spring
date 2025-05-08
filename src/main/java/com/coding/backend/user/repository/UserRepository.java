package com.coding.backend.user.repository;

import com.coding.backend.user.entity.User;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Page<User> findByNameStartingWithIgnoreCase(String name, Pageable pageable);
    Page<User> findAll(Pageable pageable);
    int countByRatingGreaterThan(Long rating);

    Optional<User> findByEmail(String email);
}
