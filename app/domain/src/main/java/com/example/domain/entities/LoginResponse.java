package com.example.domain.entities;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("ssid")
    private String ssid;

    @SerializedName("status")
    private String status;

    @SerializedName("err")
    private String err;

    public String getSsid(){
        return ssid;
    }
    public String getStatus(){
        return status;
    }
    public String getErr(){
        return err;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "ssid='" + ssid + '\'' +
                ", status='" + status + '\'' +
                ", err='" + err + '\'' +
                '}';
    }
}

