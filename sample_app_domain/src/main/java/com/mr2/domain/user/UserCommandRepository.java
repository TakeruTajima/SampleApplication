package com.mr2.domain.user;

public interface UserCommandRepository {
    void create(UserCode userCode, Name name);
    void update(User user);
    void delete(User user);
    void deleteAll();
}
