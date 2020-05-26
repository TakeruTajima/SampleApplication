package com.mr2.domain.user;

public interface UserCommandRepository {
    void save(User user);
    void delete(User user);
    void deleteAll();
}
