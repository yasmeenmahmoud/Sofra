package com.example.dell.sofra.Model;

import com.example.dell.sofra.Client_Fragments.MyOffers_Datam;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyOffers_Response {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private MyOffers_Datam data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MyOffers_Datam getData() {
        return data;
    }

    public void setData(MyOffers_Datam data) {
        this.data = data;
    }

}
