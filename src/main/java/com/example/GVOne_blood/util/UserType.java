package com.example.GVOne_blood.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserType {
    @JsonProperty("owner")
    OWNER,
    @JsonProperty("user")
    USER,
    @JsonProperty("admin")
    ADMIN
}
