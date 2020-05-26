package com.mr2.sample_app_infra.room_database.users_items;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users_items", primaryKeys = {"user_id", "items_id"})
public class UserItemEntity {
    public String user_id;
    public String item_id;
    public int quantity;

    public UserItemEntity(String user_id, String item_id, int quantity) {
        this.user_id = user_id;
        this.item_id = item_id;
        this.quantity = quantity;
    }
}
