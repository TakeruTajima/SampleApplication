package com.mr2.sample_app_infra.observer;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

//import javax.sql.DataSource;

@Dao
public interface UserListDao {
    @Query("select " +
            "u.code as user_code, " +
            "u.first_name || ' ' || u.family_name as name, " +
            "sum(ui.quantity) as weight " +
            "from users as u " +
            "inner join users_items as ui " +
            "on u._id = user_id " +
            "group by user_id;")
    List<UserHeadlineDto> getSubset();
}
