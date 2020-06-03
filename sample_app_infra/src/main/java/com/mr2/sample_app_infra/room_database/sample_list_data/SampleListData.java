package com.mr2.sample_app_infra.room_database.sample_list_data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "sample_data")
public class SampleListData {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;

    public SampleListData(String name){
        this.name = name;
    }

    public String id(){
        return String.valueOf(id);
    }

    public String name(){
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SampleListData)) return false;
        SampleListData that = (SampleListData) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
