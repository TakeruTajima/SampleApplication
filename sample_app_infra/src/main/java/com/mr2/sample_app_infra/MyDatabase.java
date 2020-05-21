package com.mr2.sample_app_infra;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mr2.sample_app_infra.items.Item;
import com.mr2.sample_app_infra.items.ItemDao;
import com.mr2.sample_app_infra.users.User;
import com.mr2.sample_app_infra.users.UserDao;
import com.mr2.sample_app_infra.users_items.UserItem;
import com.mr2.sample_app_infra.users_items.UserItemDao;

@Database(entities = {User.class, Item.class, UserItem.class}, version = 1, exportSchema = true)
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
