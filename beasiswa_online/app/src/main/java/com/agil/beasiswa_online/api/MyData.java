package com.agil.beasiswa_online.api;

import com.google.gson.annotations.SerializedName;

public class MyData {
    @SerializedName("code")
    private int codeStatus;

    @SerializedName("status")
    private String status;

    @SerializedName("mydata")
    private Data mydata;

    public int getCodeStatus() {
        return codeStatus;
    }

    public String getStatus() {
        return status;
    }

    public Data getMydata() {
        return mydata;
    }

    public static class Data {
        @SerializedName("username")
        private String username;

        public String getUsername() {
            return username;
        }
    }
}
