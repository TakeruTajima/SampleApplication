package com.mr2.sample_app_application;

public interface SampleApplicationService {
    void registerUser(String userCode, String firstName, String middleName, String familyName);
    void createItem(String name);
    void changeUserName(String userId, String firstName, String middleName, String familyName);
    void changeUserCode(String userId, String userCode);
    void changeItemName(String itemId, String name);
    void giveItemForUser(String userId, String itemId, int quantity);
    void transactionItem(String fromUserId, String toUserId, String itemId, int quantity);
    void deleteUser(String userId);
    void deleteItem(String itemId);
    void clearAll();
    //LiveDataやDataSource.Factory等のデータバインディング機能はAndroid依存なのでApplication層には書けない
//    List<UserHeadline> browsUsers();
//    UserInfo browsUserInfo();
//    List<ItemHeadline> browsItems();
//    ItemInfo browsItemInfo();
}
