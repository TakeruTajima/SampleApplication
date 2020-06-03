package com.mr2.sample_app_infra.observer;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;


//import javax.sql.DataSource;

@Dao
public interface ListDataSourceDao {
//    @Query("select " +
//            "u.code as user_code, " +
//            "u.first_name || ' ' || u.family_name as name, " +
//            "sum(ui.quantity) as weight " +
//            "from users as u " +
//            "inner join users_items as ui " +
//            "on u._id = user_id " +
//            "group by user_id;")
//    List<UserHeadlineDto> getSubset();

    @Query("select " +
            "u.code as user_code, " +
            "u.first_name || ' ' || u.family_name as name, " +
            "sum(ui.quantity) as weight " +
            "from users as u " +
            "inner join users_items as ui on u._id = user_id " +
            "group by user_id;")
    DataSource.Factory<Integer, UserHeadlineDto> getUserListSource();

    @Query("select " +
            "i._id as item_id, " +
            "i.name as name, " +
            "sum(ui.quantity) as quantity, " +
            "i.unit_name as unit_name " +
            "from items as i " +
            "inner join users_items as ui on i._id = item_id " +
            "group by item_id;")
    DataSource.Factory<Integer, ItemHeadlineDto> getItemListSource();
}
