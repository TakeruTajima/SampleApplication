package com.mr2.sample_app_infra.room_database.items;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class ItemEntity {
    @PrimaryKey @NonNull
    public String _id;
    @NonNull
    public String name;
    @NonNull
    public String unit_name;

    public ItemEntity(@NonNull String _id, @NonNull String name, @NonNull String unit_name) {
        this._id = _id;
        this.name = name;
        this.unit_name = unit_name;
    }
}
