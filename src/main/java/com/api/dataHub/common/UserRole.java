package com.api.dataHub.common;

import lombok.Getter;

@Getter
public enum UserRole {

    AMIND("ROLE_ADMIN"),
    MANAGER("ROLE_MANAGER"),
    USER("ROLE_USER");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
