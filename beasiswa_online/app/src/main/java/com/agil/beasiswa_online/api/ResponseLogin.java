package com.agil.beasiswa_online.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseLogin {
    @SerializedName("code")
    int codeStatus;

    @SerializedName("status")
    String status;

    public String getStatus() {
        return status;
    }

    public int getCodeStatus() {
        return codeStatus;
    }
}
