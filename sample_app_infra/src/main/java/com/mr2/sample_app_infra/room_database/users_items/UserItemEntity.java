package com.mr2.sample_app_infra.room_database.users_items;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users_items", primaryKeys = {"user_id", "item_id"})
public class UserItemEntity {
    @NonNull
    public String user_id;
    @NonNull
    public String item_id;
    public int quantity;

    public UserItemEntity(@NonNull String user_id, @NonNull String item_id, int quantity) {
        if (0 >= quantity) throw new IllegalArgumentException();
        this.user_id = user_id;
        this.item_id = item_id;
        this.quantity = quantity;
    }
}
