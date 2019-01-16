package com.example.dell.sofra.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Register_Datam {
    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("user")
    @Expose
    private Register_Data user;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public Register_Data getUser() {
        return user;
    }

    public void setUser(Register_Data user) {
        this.user = user;
    }
}
