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

    public ItemEntity(@NonNull String _id, @NonNull String name) {
        this._id = _id;
        this.name = name;
    }
}
