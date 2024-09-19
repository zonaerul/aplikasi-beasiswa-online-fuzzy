package com.agil.beasiswa_online.api;

import com.google.gson.annotations.SerializedName;

public class ResponseAddPendaftar {
    @SerializedName("code")
    int codeStatus;

    @SerializedName("status")
    String status;

    public int getCodeStatus() {
        return codeStatus;
    }

    public String getStatus() {
        return status;
    }
}
