package com.mr2.sample_app_infra.repositories;

import com.mr2.domain.item.Item;
import com.mr2.domain.item.ItemQueryRepository;
import com.mr2.sample_app_infra.room_database.MyDatabase;
import com.mr2.sample_app_infra.room_database.items.ItemEntity;

import java.util.ArrayList;
import java.util.List;

public class ItemQueryRepositoryImpl implements ItemQueryRepository {
    private MyDatabase db;

    public ItemQueryRepositoryImpl(MyDatabase db) {
        this.db = db;
    }

    @Override
    public boolean exists(String _id) {
        List<ItemEntity> itemEntityList = db.itemDao().findOne(_id);
        return 1 == itemEntityList.size();
    }

    @Override
    public Item findOne(String _id) {
        List<ItemEntity> itemEntityList = db.itemDao().findOne(_id);
        if (0 == itemEntityList.size()) return null;
        ItemEntity itemEntity = itemEntityList.get(0);
        return new Item(itemEntity._id, itemEntity.name);
    }

    @Override
    public List<Item> findAll() {
        List<ItemEntity> itemEntityList = db.itemDao().findAll();
        List<Item> result = new ArrayList<>();
        for (ItemEntity itemEntity : itemEntityList) {
            result.add(new Item(itemEntity._id, itemEntity.name));
        }
        return result;
    }
}
