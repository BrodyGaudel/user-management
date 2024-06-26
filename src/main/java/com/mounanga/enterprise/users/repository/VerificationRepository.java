package com.mounanga.enterprise.users.repository;

import com.mounanga.enterprise.users.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, String> {

    @Query("select v from Verification v where v.email =:email and v.code =:code")
    Optional<Verification> findByEmailAndCode(@Param("email") String email, @Param("code") String code);

    @Modifying
    @Query("delete from Verification v where v.email = :email")
    void deleteAllByEmail(@Param("email") String email);
}
