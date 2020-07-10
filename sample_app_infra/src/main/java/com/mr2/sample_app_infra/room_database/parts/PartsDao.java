package com.mr2.sample_app_infra.room_database.parts;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mr2.sample_app_infra.ui_resource.parts_register.MakerListDto;
import com.mr2.sample_app_infra.ui_resource.parts_register.ModelListDto;
import com.mr2.sample_app_infra.ui_resource.parts_register.UnitListDto;

import java.util.List;

@Dao
public interface PartsDao {
    @Insert
    void insert(PartsEntity partsEntity);
    @Update
    void update(PartsEntity partsEntity);
    @Delete
    void delete(PartsEntity partsEntity);
    @Query("select * from parts")
    List<PartsEntity> get();
    @Query("select * from parts where id = :id")
    List<PartsEntity> find(int id);
    @Query("select * from parts where model like :model and maker like :maker")
    List<PartsEntity> find(String model, String maker);

    @Query("update parts " +
            "set " +
            "version = :version+1," +
            "name = :name," +
            "model = :model," +
            "maker = :maker," +
            "price_value = :price_value," +
            "price_currency = :price_currency," +
            "unit = :unit " +
            "where id = :id and version = :version")
    void update(int version, int id,
                String name,
                String model,
                String maker,
                float price_value,
                String price_currency,
                String unit);

    //View resource queries.
    @Query("select " +
            "distinct maker as name " +
            "from parts " +
            "order by name asc")
    LiveData<List<MakerListDto>> getMakerList();

    @Query("select " +
            "distinct model as name " +
            "from parts " +
            "where maker = :maker " +
            "order by name asc")
    LiveData<List<ModelListDto>> getModelList(String maker);

    @Query("select " +
            "distinct unit as name " +
            "from parts " +
            "order by name asc")
    LiveData<List<UnitListDto>> getUnitList();

    @Query("select * from parts")
    LiveData<List<PartsEntity>> findAll();
}
