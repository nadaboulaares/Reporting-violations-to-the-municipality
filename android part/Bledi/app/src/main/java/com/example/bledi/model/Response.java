package com.example.bledi.model;

import com.google.gson.annotations.SerializedName;

public class Response {



    @SerializedName("success")
    boolean success;


    private String message;
    private String token;
    private String path;
    public String getMessage() {
        return message;
    }



    public String getPath() {return path; }
    public String getToken() {
        return token;
    }


    boolean getSuccess() {
        return success;

    }





}
