package com.mr2.sample_app_infra.items;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    void insert(Item item);
    @Update
    void update(Item item);
    @Delete
    void delete(Item item);
    @Query("select * from items")
    List<Item> findAll();
    @Query("select * from items where _id = :_id")
    List<Item> findOne(int _id);
}
