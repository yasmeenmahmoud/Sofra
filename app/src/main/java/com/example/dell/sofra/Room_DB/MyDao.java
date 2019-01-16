package com.example.dell.sofra.Room_DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.dell.sofra.Model.RestaurantItems_Data;

import java.util.List;

@Dao
public interface MyDao {
    @Insert
    void addOrder(RestaurantItems_Data ... restaurantItems_data);

    @Query("select * from myorder")
    List<RestaurantItems_Data> getOrders();
}
