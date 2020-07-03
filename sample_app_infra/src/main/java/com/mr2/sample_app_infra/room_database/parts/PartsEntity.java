package com.mr2.sample_app_infra.room_database.parts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "parts", indices = {@Index(value = {"model", "maker"}, unique = true)})
public class PartsEntity {
    public int version;
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String model;
    public String maker;
    public float price_value;
    public String price_currency;
    public String unit;

    public PartsEntity(int version, int id, String name, String model, String maker, float price_value, String price_currency, String unit) {
        this.version = version;
        this.id = id;
        this.name = name;
        this.model = model;
        this.maker = maker;
        this.price_value = price_value;
        this.price_currency = price_currency;
        this.unit = unit;
    }
}
