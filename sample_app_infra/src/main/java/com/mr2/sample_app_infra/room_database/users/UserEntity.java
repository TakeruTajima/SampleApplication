package com.mr2.sample_app_infra.room_database.users;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "users")
public class UserEntity {
    @PrimaryKey @NonNull
    public String _id;
    @NonNull
    public String code;
    @NonNull
    public String first_name;
    @NonNull
    public String middle_name;
    @NonNull
    public String family_name;

    public UserEntity(@NonNull String _id, @NonNull String code, @NonNull String first_name, @NonNull String middle_name, @NonNull String family_name) {
        this._id = _id;
        this.code = code;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.family_name = family_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        UserEntity user = (UserEntity) o;
        return _id.equals(user._id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id);
    }
}
