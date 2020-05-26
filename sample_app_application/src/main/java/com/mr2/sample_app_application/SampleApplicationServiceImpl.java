package com.mr2.sample_app_application;

import com.mr2.domain.item.Item;
import com.mr2.domain.item.ItemCommandRepository;
import com.mr2.domain.item.ItemQueryRepository;
import com.mr2.domain.user.Name;
import com.mr2.domain.user.User;
import com.mr2.domain.user.UserCode;
import com.mr2.domain.user.UserCommandRepository;
import com.mr2.domain.user.UserQueryRepository;
import com.mr2.domain.user.UserService;

class SampleApplicationServiceImpl implements SampleApplicationService {
    private UserQueryRepository userQuery;
    private UserCommandRepository userCommand;
    private ItemQueryRepository itemQuery;
    private ItemCommandRepository itemCommand;

    public SampleApplicationServiceImpl(
            UserQueryRepository userQuery,
            UserCommandRepository userCommand,
            ItemQueryRepository itemQuery,
            ItemCommandRepository itemCommand
    ) {
        if (null == userCommand || null == userQuery || null == itemCommand || null == itemQuery)
                throw new IllegalArgumentException();
        this.userQuery = userQuery;
        this.userCommand = userCommand;
        this.itemQuery = itemQuery;
        this.itemCommand = itemCommand;
    }

    @Override
    public void registerUser(String userCode, String firstName, String middleName, String familyName) {
        if (-1 != Name.invariantInspection(firstName, middleName, familyName)) return;
        User user = UserService.createUser(userQuery,
                new UserCode(userCode),
                new Name(firstName,middleName,familyName));
        userCommand.save(user);
    }

    @Override
    public void createItem(String name) {
        if (-1 != Item.invariantInspection(name)) return;
        Item item = new Item(name);
        itemCommand.save(item);
    }

    @Override
    public void changeUserName(String userId, String firstName, String middleName, String familyName) {
        if (-1 != Name.invariantInspection(firstName, middleName, familyName)) return;
        User user = userQuery.findOne(userId);
        if (null == user) return;
        user.changeName(new Name(firstName, middleName, familyName));
        userCommand.save(user);
    }

    @Override
    public void changeUserCode(String userId, String userCode) {
        if (-1 != UserCode.invariantInspection(userCode)) return;
        User user = userQuery.findOne(userId);
        if (null == user) return;
        UserService.changeUserCode(userQuery, user, new UserCode(userCode));
        userCommand.save(user);
    }

    @Override
    public void changeItemName(String itemId, String name) {
        if (-1 != Item.invariantInspection(name)) return;
        Item item = itemQuery.findOne(itemId);
        if (null == item) return;
        item.changeName(name);
        itemCommand.save(item);
    }

    @Override
    public void giveItemForUser(String userId, String itemId, int quantity) {
        if (itemQuery.exists(itemId)) return;
        User user = userQuery.findOne(userId);
        if (null == user) return;
        user.takeItems(itemId, quantity);
        userCommand.save(user);
    }

    @Override
    public void transactionItem(String fromUserId, String toUserId, String itemId, int quantity) {
        Item item = itemQuery.findOne(itemId);
        if (null == item) return;
        User fromUser = userQuery.findOne(fromUserId);
        User toUser = userQuery.findOne(toUserId);
        if (null == fromUser || null == toUser) return;
        UserService.tradeItem(fromUser, toUser, item, quantity);
        userCommand.save(fromUser);
        userCommand.save(toUser);
    }

    @Override
    public void deleteUser(String userId) {
        User user = userQuery.findOne(userId);
        if (null == user) return;
        userCommand.delete(user);
    }

    @Override
    public void deleteItem(String itemId) {
        Item item = itemQuery.findOne(itemId);
        if (null == item) return;
        itemCommand.delete(item);
    }

    @Override
    public void clearAll() {
        userCommand.deleteAll();
        itemCommand.deleteAll();
    }
}
