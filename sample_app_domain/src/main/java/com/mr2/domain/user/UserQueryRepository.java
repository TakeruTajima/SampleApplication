package com.mr2.domain.user;

import java.util.List;

public interface UserQueryRepository {
    boolean exists(String user_id);
    User findOne(String _id);
    User findByUserCode(UserCode userCode);
    List<User> findAll();
}
