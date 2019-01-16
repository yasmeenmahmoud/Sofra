package com.example.dell.sofra.Room_DB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.dell.sofra.Model.RestaurantItems_Data;

@Database(entities = {RestaurantItems_Data.class}, version = 1, exportSchema = false)
public abstract class MyAppDataBase extends RoomDatabase {
    public abstract MyDao myDao();

    private static MyAppDataBase myAppDataBase;

    public static synchronized MyAppDataBase getInstance(Context context) {
        if (myAppDataBase == null) {
            myAppDataBase = Room.databaseBuilder(context.getApplicationContext(), MyAppDataBase.class, "Orders_database")
                    .fallbackToDestructiveMigration()
                   // .allowMainThreadQueries()
                    .build();
        }
        return myAppDataBase;
    }
    public static void destroyInstance() {
        myAppDataBase = null;
    }
}
