package com.mounanga.enterprise.users.dto.request;

import com.mounanga.enterprise.users.enums.StatusIAM;

public class UserIamRequest {
    private String id;
    private String firstname;
    private String lastname;
    private String username;
    private StatusIAM status;
    private String password;

    public UserIamRequest() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public StatusIAM getStatus() {
        return status;
    }

    public void setStatus(StatusIAM status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserIamRequest{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", status=" + status +
                ", password='" + password + '\'' +
                '}';
    }
}
