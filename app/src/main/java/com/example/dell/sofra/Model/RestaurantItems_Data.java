package com.example.dell.sofra.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "myorder")
public class RestaurantItems_Data {
    @PrimaryKey(autoGenerate = true)
    private int item_id;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("preparing_time")
    @Expose
    private String preparingTime;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;

    @SerializedName("photo_url")
    @Expose
    private String photoUrl;
    private String quentity;

    public String getDeliverycost() {
        return deliverycost;
    }

    public void setDeliverycost(String deliverycost) {
        this.deliverycost = deliverycost;
    }

    private String deliverycost;

    public RestaurantItems_Data(Integer id, String createdAt, String updatedAt, String name, String description, String price, String preparingTime, String photo, String restaurantId, String photoUrl, String quentity, String deliverycost) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.description = description;
        this.price = price;
        this.preparingTime = preparingTime;
        this.photo = photo;
        this.restaurantId = restaurantId;
        this.photoUrl = photoUrl;
        this.quentity = quentity;
        this.deliverycost = deliverycost;
    }


    public RestaurantItems_Data() {
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getQuentity() {
        return quentity;
    }

    public void setQuentity(String quentity) {
        this.quentity = quentity;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPreparingTime() {
        return preparingTime;
    }

    public void setPreparingTime(String preparingTime) {
        this.preparingTime = preparingTime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
