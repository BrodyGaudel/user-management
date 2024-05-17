package com.mounanga.enterprise.users.dto.request;

import com.mounanga.enterprise.users.enums.Gender;

import java.time.LocalDate;

public class UserRootRequest {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private String placeOfBirth;
    private String nationality;
    private Gender gender;
    private String email;
    private WorkspaceRequest workspace;

    public UserRootRequest() {
        super();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public WorkspaceRequest getWorkspace() {
        return workspace;
    }

    public void setWorkspace(WorkspaceRequest workspace) {
        this.workspace = workspace;
    }

    @Override
    public String toString() {
        return "UserRootRequest{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", placeOfBirth='" + placeOfBirth + '\'' +
                ", nationality='" + nationality + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", workspace=" + workspace +
                '}';
    }
}
