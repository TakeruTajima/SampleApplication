package com.mr2.sample_app_infra.room_database.sample_list_data;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SampleDao {
    @Insert
    void insert(SampleListData data);
    @Update
    void update(SampleListData data);
    @Delete
    void delete(SampleListData data);
    @Query("select * from sample_data")
    DataSource.Factory<Integer, SampleListData> getPagedData();
    @Query("select * from sample_data")
    List<SampleListData> findAll();
}
