package com.mr2.sample_app_infra.users_items;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserItemDao {
    @Insert
    public void insert(UserItem userItem);
    @Update
    void update(UserItem userItem);
    @Delete
    void delete(UserItem userItem);
    @Query("select * from users_items")
    List<UserItem> findAll();
    @Query("select * from users_items where _id = :_id")
    List<UserItem> findOne(int _id);
    @Query("select * from users_items where user_id = :user_id")
    List<UserItem> findByUserId(String user_id);
    @Query("select * from users_items where item_id = :item_id")
    List<UserItem> findByItemId(String item_id);
}
