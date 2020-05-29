package com.mr2.sample_app_infra.room_database.users;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(UserEntity user);
    @Update
    void update(UserEntity user);
    @Delete
    void delete(UserEntity user);
    @Query("delete from users")
    void deleteAll();
    @Query("select * from users order by code asc")
    List<UserEntity> getAll();
    @Query("select * from users where _id = :id order by code asc")
    List<UserEntity> findById(String id);
    @Query("select * from users where code = :code order by code asc")
    List<UserEntity> findByUserCode(String code);
}
