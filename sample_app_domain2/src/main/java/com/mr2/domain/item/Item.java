package com.mr2.domain.item;
import java.util.Objects;
import java.util.UUID;

public class Item {
    private final String _id;
    private String name;
    private String unitName;

    public static int invariantInspection(String name, String unitName){
        if (null == name || 0 == name.length()) return 1;
        if (null == unitName || 0 == unitName.length()) return 2;
        return -1;
    }

    public Item(String name, String unitName){
        this._id = UUID.randomUUID().toString();
        this.name = name;
        this.unitName = unitName;
        if (-1 != invariantInspection(this.name, this.unitName))
            throw new IllegalArgumentException("");
    }

    public Item(String _id, String name, String unitName) {
        this._id = _id;
        this.name = name;
        this.unitName = unitName;
        if (-1 != invariantInspection(this.name, this.unitName))
            throw new IllegalArgumentException("");
    }

    public String _id(){ return _id; }

    public String name() { return name; }

    public void changeName(String name){
        if (-1 != invariantInspection(name, unitName)) this.name = name;
    }

    public void changeUnitName(String unitName){
        if (-1 != invariantInspection(name, unitName)) this.unitName = unitName;
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

    public String unitName() {
        return unitName;
    }
}
