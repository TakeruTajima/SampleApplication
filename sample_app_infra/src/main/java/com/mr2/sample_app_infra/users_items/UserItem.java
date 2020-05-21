package com.mr2.sample_app_infra.users_items;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users_items")
public class UserItem {
    @PrimaryKey(autoGenerate = true)
    public int _id;
    public String user_id;
    public String item_id;
    public int quantity;

}
