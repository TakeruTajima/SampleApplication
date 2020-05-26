package com.mr2.sample_app_infra.room_database.users_items;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserItemDao {
    @Insert
    public void insert(UserItemEntity userItemEntity);
    @Update
    void update(UserItemEntity userItemEntity);
    @Delete
    void delete(UserItemEntity userItemEntity);
    @Query("delete from users_items")
    void deleteAll();
    @Query("select * from users_items")
    List<UserItemEntity> findAll();
    @Query("select * from users_items where user_id = :user_id")
    List<UserItemEntity> findByUserId(String user_id);
    @Query("select * from users_items where item_id = :item_id")
    List<UserItemEntity> findByItemId(String item_id);
    @Query("select * from users_items where item_id = :item_id and user_id = :user_id")
    List<UserItemEntity> findOne(String user_id, String item_id);
}
