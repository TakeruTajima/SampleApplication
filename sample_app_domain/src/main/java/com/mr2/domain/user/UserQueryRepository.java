package com.mr2.domain.user;

import java.util.List;

public interface UserQueryRepository {
    User findOne(String _id);
    List<User> findByUserCode(UserCode userCode);
    List<User> findAll();
}
