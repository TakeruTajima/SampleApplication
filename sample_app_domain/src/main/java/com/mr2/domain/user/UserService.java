package com.mr2.domain.user;

import com.mr2.domain.item.Item;

import java.util.List;

public class UserService {
    public static boolean isDuplicationUserCode(UserQueryRepository qr, UserCode userCode){
        return 0 != qr.findByUserCode(userCode).size();
    }

    public static void deliverItem(Item targetItem, User from, User to, int quantity){
        if (quantity > from.numOfHoldings(targetItem)) throw new IllegalArgumentException();
        from.releaseItems(targetItem, quantity);
        to.getItems(targetItem, quantity);
    }
}
