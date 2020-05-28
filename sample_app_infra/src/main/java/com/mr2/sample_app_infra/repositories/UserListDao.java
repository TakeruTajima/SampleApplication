package com.mr2.sample_app_infra.repositories;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import javax.sql.DataSource;

@Dao
public interface UserListDao {
//    @Query("select \n" +
//            "u.code as user_code,\n" +
//            "u.first_name || ' ' || u.family_name as name,\n" +
//            "sum(ui.quantity) as weight\n" +
//            "from users as u\n" +
//            "inner join users_items as ui \n" +
//            "\ton u._id = user_id\n" +
//            "group by user_id;")
//    public List<UserHeadlineDto> getSubsetDto();
}
