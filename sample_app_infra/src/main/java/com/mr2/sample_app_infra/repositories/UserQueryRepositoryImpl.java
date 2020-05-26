package com.mr2.sample_app_infra.repositories;

import androidx.annotation.NonNull;

import com.mr2.domain.user.Name;
import com.mr2.domain.user.User;
import com.mr2.domain.user.UserCode;
import com.mr2.domain.user.UserQueryRepository;
import com.mr2.sample_app_infra.room_database.MyDatabase;
import com.mr2.sample_app_infra.room_database.users.UserEntity;
import com.mr2.sample_app_infra.room_database.users_items.UserItemEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserQueryRepositoryImpl implements UserQueryRepository {
    @NonNull private MyDatabase db;

    public UserQueryRepositoryImpl(@NonNull MyDatabase db) {
        this.db = db;
    }

    @Override
    public boolean exists(String user_id) {
        List<UserEntity> userList = db.userDao().findById(user_id);
        return !(0 == userList.size());
    }

    @Override
    public User findOne(String _id) {
        List<UserEntity> userEntityList = db.userDao().findById(_id);
        if (1 != userEntityList.size()) throw new IllegalArgumentException();
        UserEntity userEntity = userEntityList.get(0);
        Map<String,Integer> items = new HashMap<>();
        List<UserItemEntity> userItemEntityList = db.userItemDao().findByUserId(_id);
        for (UserItemEntity userItemEntity : userItemEntityList) {
            items.put(userItemEntity.item_id, userItemEntity.quantity);
        }
        return new User(
                userEntity._id,
                new UserCode(userEntity.code),
                new Name(userEntity.first_name,userEntity.middle_name,userEntity.family_name),
                items
        );
    }

    @Override
    public User findByUserCode(UserCode userCode) {
        List<UserEntity> userList = db.userDao().findByUserCode(userCode.value());
        if (1 != userList.size()) return null;
        UserEntity user = userList.get(0);
        List<UserItemEntity> userItemList = db.userItemDao().findByUserId(user._id);
        Map<String,Integer> items = new HashMap<>();
        for (UserItemEntity userItemEntity : userItemList) {
            items.put(userItemEntity.item_id, userItemEntity.quantity);
        }
        return new User(
                user._id,
                new UserCode(user.code),
                new Name(user.first_name,user.middle_name,user.family_name),
                items
        );
    }

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        List<UserEntity> userEntityList = db.userDao().getAll();
        for (UserEntity userEntity : userEntityList) {
            List<UserItemEntity> userItemEntityList = db.userItemDao().findByUserId(userEntity._id);
            Map<String,Integer> items = new HashMap<>();
            for (UserItemEntity userItemEntity : userItemEntityList) {
                items.put(userItemEntity.item_id, userItemEntity.quantity);
            }
            result.add(new User(
                    userEntity._id,
                    new UserCode(userEntity.code),
                    new Name(userEntity.first_name, userEntity.middle_name, userEntity.family_name),
                    items
            ));
        }
        return result;
    }
}
