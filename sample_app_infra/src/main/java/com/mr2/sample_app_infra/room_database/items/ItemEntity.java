package com.mr2.sample_app_infra.room_database.items;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class ItemEntity {
    @PrimaryKey
    public String _id;
    public String name;

    public ItemEntity(String _id, String name) {
        this._id = _id;
        this.name = name;
    }
}
