package com.mr2.sample_app_infra.room_database.items;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    void insert(ItemEntity item);
    @Update
    void update(ItemEntity item);
    @Delete
    void delete(ItemEntity item);
    @Query("delete from items")
    void deleteAll();
    @Query("select * from items")
    List<ItemEntity> findAll();
    @Query("select * from items where _id = :_id")
    List<ItemEntity> findOne(String _id);
}
