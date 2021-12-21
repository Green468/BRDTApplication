package com.example.domain.entities;

import com.google.gson.annotations.SerializedName;

public class Users {
    @SerializedName("_id")
    private String id;

    @SerializedName("token")
    private String token;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;
}
