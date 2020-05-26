package com.mr2.sample_app_infra.repositories;

import com.mr2.domain.item.Item;
import com.mr2.domain.item.ItemCommandRepository;
import com.mr2.sample_app_infra.room_database.MyDatabase;
import com.mr2.sample_app_infra.room_database.items.ItemEntity;

import java.util.List;

public class ItemCommandRepositoryImpl implements ItemCommandRepository {
    private MyDatabase db;

    public ItemCommandRepositoryImpl(MyDatabase db) {
        this.db = db;
    }

    @Override
    public void save(final Item item) {
        db.runInTransaction(new Runnable() {
            @Override
            public void run() {
                ItemEntity itemEntity = new ItemEntity(item._id(), item.name());
                List<ItemEntity> itemList = db.itemDao().findOne(item._id());
                if (2 <= itemList.size()) throw new IllegalStateException();
                if (0 == itemList.size()){
                    db.itemDao().insert(itemEntity);
                }else {
                    db.itemDao().update(itemEntity);
                }
            }
        });
    }

    @Override
    public void delete(final Item item) {
        db.runInTransaction(new Runnable() {
            @Override
            public void run() {
                ItemEntity itemEntity = new ItemEntity(item._id(), item.name());
                List<ItemEntity> itemList = db.itemDao().findOne(item._id());
                if (0 == itemList.size()) return;
                db.itemDao().delete(itemEntity);
            }
        });
    }

    @Override
    public void deleteAll() {
        db.itemDao().deleteAll();
    }
}
