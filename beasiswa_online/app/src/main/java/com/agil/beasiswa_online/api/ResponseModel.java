package com.agil.beasiswa_online.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {

    @SerializedName("code")
    private int code;

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private MyData myData;


    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public MyData getMyData() {
        return myData;
    }


}
