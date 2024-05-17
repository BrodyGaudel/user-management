package com.mounanga.enterprise.users.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, updatable = false)
    private String email;

    @Column(nullable = false, updatable = false)
    private String code;

    @Column(nullable = false, updatable = false)
    private LocalDateTime expiryDateTime;

    public Verification(String email, String code, LocalDateTime expiryDateTime) {
        this.email = email;
        this.code = code;
        this.expiryDateTime = expiryDateTime;
    }

    public Verification() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpiryDateTime() {
        return expiryDateTime;
    }

    public void setExpiryDateTime(LocalDateTime expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
    }

    public boolean isExpired(){
        return expiryDateTime.isAfter(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "Verification{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", code='" + code + '\'' +
                ", expiryDateTime=" + expiryDateTime +
                '}';
    }
}
