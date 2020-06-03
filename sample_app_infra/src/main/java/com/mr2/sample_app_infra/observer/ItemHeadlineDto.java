package com.mr2.sample_app_infra.observer;

import java.util.Objects;

public class ItemHeadlineDto {
    public String item_id;
    public String name;
    public int quantity;
    public String unit_name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemHeadlineDto)) return false;
        ItemHeadlineDto that = (ItemHeadlineDto) o;
        return quantity == that.quantity &&
                Objects.equals(item_id, that.item_id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(unit_name, that.unit_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item_id, name, quantity, unit_name);
    }
}
