package com.mounanga.enterprise.users.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(name = "user_root_id", referencedColumnName = "id", nullable = false, unique = true)
    private UserRoot userRoot;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserIam> iamList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserRoot getUserRoot() {
        return userRoot;
    }

    public void setUserRoot(UserRoot userRoot) {
        this.userRoot = userRoot;
    }

    public List<UserIam> getIamList() {
        return iamList;
    }

    public void setIamList(List<UserIam> iamList) {
        this.iamList = iamList;
    }
}
