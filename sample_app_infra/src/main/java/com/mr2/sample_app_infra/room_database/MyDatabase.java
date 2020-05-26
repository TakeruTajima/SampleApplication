package com.mr2.sample_app_infra.room_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mr2.sample_app_infra.room_database.items.ItemDao;
import com.mr2.sample_app_infra.room_database.items.ItemEntity;
import com.mr2.sample_app_infra.room_database.users.UserDao;
import com.mr2.sample_app_infra.room_database.users.UserEntity;
import com.mr2.sample_app_infra.room_database.users_items.UserItemEntity;
import com.mr2.sample_app_infra.room_database.users_items.UserItemDao;

@Database(entities = {UserEntity.class, ItemEntity.class, UserItemEntity.class}, version = 1, exportSchema = true)
public abstract class MyDatabase extends RoomDatabase {
    private static MyDatabase instance;

    public static MyDatabase getInstance(Context context){
        if (null != instance) return instance;
        instance = Room.databaseBuilder(context,
                MyDatabase.class,
                "my_database").build();
        return instance;
    }

    public abstract UserDao userDao();
    public abstract ItemDao itemDao();
    public abstract UserItemDao userItemDao();
}
