package com.mr2.domain.item;
import java.util.Objects;
import java.util.UUID;

public class Item {
    private final String _id;
    private String name;

    public static int invariantInspection(String name){
        if (null == name || 0 == name.length()) return 1;
        return -1;
    }

    public Item(String name){
        this._id = UUID.randomUUID().toString();
        this.name = name;
        if (-1 != invariantInspection(this.name))
            throw new IllegalArgumentException("");
    }

    public Item(String _id, String name) {
        this._id = _id;
        this.name = name;
        if (-1 != invariantInspection(this.name))
            throw new IllegalArgumentException("");
    }

    public String _id(){ return _id; }

    public String name() { return name; }

    public void changeName(String name){
        if (-1 != invariantInspection(name)) this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(_id, item._id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id);
    }
}
