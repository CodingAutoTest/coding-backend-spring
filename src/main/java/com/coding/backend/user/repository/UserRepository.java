package com.coding.backend.user.repository;

import com.coding.backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.id = :userId")
    Optional<User> findUserById(@Param("userId") Integer userId);

    Optional<User> findByEmail(String email);
    Page<User> findByNameStartingWithIgnoreCase(String name, Pageable pageable);
    Page<User> findAll(Pageable pageable);
    int countByRatingGreaterThan(Long rating);
    boolean existsByNameAndIdNot(String name, Integer id);
}
