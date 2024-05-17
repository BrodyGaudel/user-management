package com.mounanga.enterprise.users.entity;

import com.mounanga.enterprise.users.enums.StatusIAM;
import jakarta.persistence.*;

@Entity
public class UserIam extends User {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusIAM status;

    @ManyToOne
    private Workspace workspace;

    public UserIam() {
        super();
    }

    public StatusIAM getStatus() {
        return status;
    }

    public void setStatus(StatusIAM status) {
        this.status = status;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }
}
