package com.example.validation.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Designation {
    ENGINEER,
    MANAGER,
    LEAD,
    ARCHITECT,
    INTERN;

    @JsonCreator
    public static Designation from(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null; // or return a default like DEVELOPER
        }
        return Designation.valueOf(value.toUpperCase());
    }
}

