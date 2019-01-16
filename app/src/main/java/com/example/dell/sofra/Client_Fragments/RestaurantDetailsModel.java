package com.example.dell.sofra.Client_Fragments;

public class RestaurantDetailsModel {
    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealDetails() {
        return mealDetails;
    }

    public void setMealDetails(String mealDetails) {
        this.mealDetails = mealDetails;
    }

    public String getMealdetails2() {
        return mealdetails2;
    }

    public void setMealdetails2(String mealdetails2) {
        this.mealdetails2 = mealdetails2;
    }

    public String getPricetext() {
        return pricetext;
    }

    public void setPricetext(String pricetext) {
        this.pricetext = pricetext;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getMeal_image() {
        return meal_image;
    }

    public void setMeal_image(int meal_image) {
        this.meal_image = meal_image;
    }

    String mealName,mealDetails,mealdetails2,pricetext,price;
    int meal_image;

    public String getCommentname() {
        return commentname;
    }

    public void setCommentname(String commentname) {
        this.commentname = commentname;
    }

    public String getCommentdate() {
        return commentdate;
    }

    public void setCommentdate(String commentdate) {
        this.commentdate = commentdate;
    }

    public String getCommentcontent() {
        return commentcontent;
    }

    public void setCommentcontent(String commentcontent) {
        this.commentcontent = commentcontent;
    }

    public int getCommentrating() {
        return commentrating;
    }

    public void setCommentrating(int commentrating) {
        this.commentrating = commentrating;
    }

    public String getB_ordername() {
        return b_ordername;
    }

    public void setB_ordername(String b_ordername) {
        this.b_ordername = b_ordername;
    }

    public String getB_price() {
        return b_price;
    }

    public void setB_price(String b_price) {
        this.b_price = b_price;
    }

    public String getB_quentitytext() {
        return b_quentitytext;
    }

    public void setB_quentitytext(String b_quentitytext) {
        this.b_quentitytext = b_quentitytext;
    }

    public int getB_orderimage() {
        return b_orderimage;
    }

    public void setB_orderimage(int b_orderimage) {
        this.b_orderimage = b_orderimage;
    }

    String b_ordername,b_price,b_quentitytext;
    int b_orderimage;
    String commentname,commentdate,commentcontent;
    int commentrating;
}
