package com.mr2.sample_app_infra.ui_resource.parts_register;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.mr2.sample_app_infra.ui_resource.SingleStringListResource;

import java.util.List;

@Dao
public interface PartsRegisterResourceDao {

    //View resource queries.

    @Query("select " +
            "maker as maker_name, " +
            "model as model " +
            "from parts " +
            "order by model asc")
    LiveData<List<MakerModelListDto>> getMakerModelList();

    @Query("select " +
            "distinct maker as value " +
            "from parts " +
            "order by value asc")
    LiveData<List<SingleStringListResource>> getMakerList();

    @Query("select " +
            "distinct unit as value " +
            "from parts " +
            "order by value asc")
    LiveData<List<SingleStringListResource>> getUnitList();
}
