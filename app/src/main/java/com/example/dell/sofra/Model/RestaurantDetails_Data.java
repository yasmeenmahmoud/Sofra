package com.example.dell.sofra.Model;

import android.graphics.Region;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantDetails_Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("region_id")
    @Expose
    private String regionId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("delivery_method_id")
    @Expose
    private String deliveryMethodId;
    @SerializedName("delivery_days")
    @Expose
    private String deliveryDays;
    @SerializedName("delivery_cost")
    @Expose
    private String deliveryCost;
    @SerializedName("minimum_charger")
    @Expose
    private String minimumCharger;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("whatsapp")
    @Expose
    private String whatsapp;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("availability")
    @Expose
    private String availability;
    @SerializedName("activated")
    @Expose
    private String activated;
    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("region")
    @Expose
    private Region_Data region;
    @SerializedName("categories")
    @Expose
    private List<Category_Data> categories = null;
    @SerializedName("delivery_method")
    @Expose
    private Object deliveryMethod;
    @SerializedName("delivery_times")
    @Expose
    private List<Object> deliveryTimes = null;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @SerializedName("photo_url")
    @Expose
    private String photoUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(String deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public String getDeliveryDays() {
        return deliveryDays;
    }

    public void setDeliveryDays(String deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    public String getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(String deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getMinimumCharger() {
        return minimumCharger;
    }

    public void setMinimumCharger(String minimumCharger) {
        this.minimumCharger = minimumCharger;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getActivated() {
        return activated;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Region_Data getRegion() {
        return region;
    }

    public void setRegion(Region_Data region) {
        this.region = region;
    }

    public List<Category_Data> getCategories() {
        return categories;
    }

    public void setCategories(List<Category_Data> categories) {
        this.categories = categories;
    }

    public Object getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(Object deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public List<Object> getDeliveryTimes() {
        return deliveryTimes;
    }

    public void setDeliveryTimes(List<Object> deliveryTimes) {
        this.deliveryTimes = deliveryTimes;
    }
}
