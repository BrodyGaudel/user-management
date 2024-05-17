package com.mounanga.enterprise.users.dto.request;

public class WorkspaceRequest {
    private String name;
    private String description;

    public WorkspaceRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public WorkspaceRequest() {
        super();
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

    @Override
    public String toString() {
        return "WorkspaceRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
