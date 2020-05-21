package com.mr2.sample_app_infra.items;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class Item {
    @PrimaryKey
    public String _id;
    public String name;

    public Item(String _id, String name) {
        this._id = _id;
        this.name = name;
    }
}
