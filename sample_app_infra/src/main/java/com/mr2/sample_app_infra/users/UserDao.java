package com.mr2.sample_app_infra.users;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);
    @Update
    void update(User user);
    @Delete
    void delete(User user);
    @Query("select * from users order by code asc")
    List<User> getAll();
    @Query("select * from users where _id = :_id order by code asc")
    List<User> findById(String _id);
    @Query("select * from users where code = :code order by code asc")
    List<User> findByUserCode(String code);
}
