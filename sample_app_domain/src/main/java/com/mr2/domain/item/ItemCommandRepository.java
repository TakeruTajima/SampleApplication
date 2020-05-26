package com.mr2.domain.item;

public interface ItemCommandRepository {
    void save(Item item);
    void delete(Item item);
    void deleteAll();
}
