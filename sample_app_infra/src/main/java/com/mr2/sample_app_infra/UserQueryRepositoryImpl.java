package com.mr2.sample_app_infra;

import com.mr2.domain.user.User;
import com.mr2.domain.user.UserCode;
import com.mr2.domain.user.UserQueryRepository;

import java.util.List;

public class UserQueryRepositoryImpl implements UserQueryRepository {
    @Override
    public User findOne(String _id) {
        return null;
    }

    @Override
    public List<User> findByUserCode(UserCode userCode) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
