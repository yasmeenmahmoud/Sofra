package com.example.dell.sofra.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddOrder_Datam {
    @SerializedName("order")
    @Expose
    private AddOrder_Data order;

    public AddOrder_Data getOrder() {
        return order;
    }

    public void setOrder(AddOrder_Data order) {
        this.order = order;
    }
}
