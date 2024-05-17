package com.mounanga.enterprise.users.repository;

import com.mounanga.enterprise.users.entity.UserRoot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRootRepository extends JpaRepository<UserRoot, String> {

    Optional<UserRoot> findByUsername(String username);

    Optional<UserRoot> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
