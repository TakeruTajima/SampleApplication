package com.mr2.sample_app_infra.room_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mr2.sample_app_infra.ui_resource.ListDataSourceDao;
import com.mr2.sample_app_infra.room_database.items.ItemDao;
import com.mr2.sample_app_infra.room_database.items.ItemEntity;
import com.mr2.sample_app_infra.room_database.parts.PartsDao;
import com.mr2.sample_app_infra.room_database.parts.PartsEntity;
import com.mr2.sample_app_infra.room_database.sample_list_data.SampleDao;
import com.mr2.sample_app_infra.room_database.sample_list_data.SampleListData;
import com.mr2.sample_app_infra.room_database.users.UserDao;
import com.mr2.sample_app_infra.room_database.users.UserEntity;
import com.mr2.sample_app_infra.room_database.users_items.UserItemEntity;
import com.mr2.sample_app_infra.room_database.users_items.UserItemDao;
import com.mr2.sample_app_infra.ui_resource.parts_register.PartsRegisterResourceDao;

import java.util.List;

@Database(entities = {
        PartsEntity.class,
        UserEntity.class,
        ItemEntity.class,
        UserItemEntity.class,
        SampleListData.class},
        version = 1, exportSchema = true)
public abstract class MyDatabase extends RoomDatabase {
    private static MyDatabase instance;

    public static MyDatabase getInstance(Context context){
        if (null != instance) return instance;
        instance = Room.databaseBuilder(context,
                MyDatabase.class,
                "my_database").build();
//        initiate(instance);
        return instance;
    }

    private static void initiate(final MyDatabase instance){
        new Thread(()->{
            List<SampleListData> list = instance.sampleDao().findAll();
            if (0 != list.size()) return;
            for (int i = 0; i < 30000; i++) {
                SampleListData item = new SampleListData(i + "th item");
                instance.sampleDao().insert(item);
            }
        }).start();
    }

    public abstract UserDao userDao();
    public abstract ItemDao itemDao();
    public abstract UserItemDao userItemDao();
    public abstract ListDataSourceDao listDataSourceDao();
    public abstract SampleDao sampleDao();
    public abstract PartsDao partsDao();
    public abstract PartsRegisterResourceDao partsRegisterResourceDao();
}
