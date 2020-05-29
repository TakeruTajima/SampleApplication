package com.mr2.domain.item;

import java.util.List;

public interface ItemQueryRepository {
    boolean exists(String _id);
    Item findOne(String _id);
    List<Item> findAll();
}
