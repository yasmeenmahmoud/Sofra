package com.example.dell.sofra.Client_Fragments;

public class HomeModelClass {
    public String getRstrauntName() {
        return rstrauntName;
    }

    public void setRstrauntName(String rstrauntName) {
        this.rstrauntName = rstrauntName;
    }

    public String getMeals() {
        return meals;
    }

    public void setMeals(String meals) {
        this.meals = meals;
    }

    public String getMinimumorder() {
        return minimumorder;
    }

    public void setMinimumorder(String minimumorder) {
        this.minimumorder = minimumorder;
    }

    public String getArrivalcost() {
        return arrivalcost;
    }

    public void setArrivalcost(String arrivalcost) {
        this.arrivalcost = arrivalcost;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    private String rstrauntName;
    private String meals;
    private String minimumorder;
    private String arrivalcost;
    private String ordertime;

    public String getOpennow() {
        return opennow;
    }

    public void setOpennow(String opennow) {
        this.opennow = opennow;
    }

    private String opennow;

    public int getRestaurant_image() {
        return restaurant_image;
    }

    public void setRestaurant_image(int restaurant_image) {
        this.restaurant_image = restaurant_image;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    private  int rating;
    private  int restaurant_image;
//    public HomeModelClass(String rstrauntName, String meals, String minimumorder,
//                          String arrivalcost, String ordertime, String opennow,int restaurant_image) {
//        this.rstrauntName = rstrauntName;
//        this.meals = meals;
//        this.opennow = opennow;
//        this.minimumorder = minimumorder;
//        this.arrivalcost = arrivalcost;
//        this.ordertime = ordertime;
//        this.restaurant_image=restaurant_image;
//
//    }
}
