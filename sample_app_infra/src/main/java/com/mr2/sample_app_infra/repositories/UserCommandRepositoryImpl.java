package com.mr2.sample_app_infra.repositories;

import androidx.annotation.NonNull;

import com.mr2.domain.user.User;
import com.mr2.domain.user.UserCommandRepository;
import com.mr2.sample_app_infra.room_database.MyDatabase;
import com.mr2.sample_app_infra.room_database.users.UserEntity;
import com.mr2.sample_app_infra.room_database.users_items.UserItemEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserCommandRepositoryImpl implements UserCommandRepository {
    @NonNull private MyDatabase db;

    public UserCommandRepositoryImpl(@NonNull MyDatabase db) {
        this.db = db;
    }

    @Override
    public void save(@NonNull final User user) {
        db.runInTransaction(new Runnable() {
            @Override
            public void run() {

                List<UserEntity> userEntityList = db.userDao().findById(user._id());
                if (null == userEntityList || 2 <= userEntityList.size()) throw new AssertionError();
                UserEntity userEntity = new UserEntity(
                        user._id(),
                        user.userCode().value(),
                        user.name().firstName(),
                        user.name().middleName(),
                        user.name().lastName()
                );
                if (0 == userEntityList.size())
                    db.userDao().insert(userEntity);
                else {
                    db.userDao().update(userEntity);
                }
                Set<Map.Entry<String,Integer>> items = user.holdingItems().entrySet();
                for (Map.Entry<String, Integer> item : items) {
                    List<UserItemEntity> uil = db.userItemDao().findOne(user._id(),item.getKey());
                    int quantity = (null == item.getValue() ? 0 : item.getValue());
                    if (0 == uil.size())
                        db.userItemDao().insert(new UserItemEntity(user._id(), item.getKey(), quantity));
                    else {
                        UserItemEntity uie = uil.get(0);
                        uie.quantity = quantity;
                        db.userItemDao().update(uie);
                    }
                }
            }
        });
    }

    @Override
    public void delete(final User user) {
        db.runInTransaction(new Runnable() {
            @Override
            public void run() {
                List<UserItemEntity> userItemEntityList = db.userItemDao().findByUserId(user._id());
                for (UserItemEntity userItemEntity : userItemEntityList) {
                    db.userItemDao().delete(userItemEntity);
                }
                List<UserEntity> userEntityList = db.userDao().findById(user._id());
                db.userDao().delete(userEntityList.get(0));
            }
        });
    }

    @Override
    public void deleteAll() {
        db.runInTransaction(new Runnable() {
            @Override
            public void run() {
                db.userItemDao().deleteAll();
                db.userDao().deleteAll();
            }
        });
    }
}
