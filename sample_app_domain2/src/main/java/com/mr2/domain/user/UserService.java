package com.mr2.domain.user;
import com.mr2.domain.item.*;
public class UserService {
    public static User createUser(UserQueryRepository qr, UserCode userCode, Name name){
        User user = null; //qr.findByUserCode(userCode);
        if (null != user) throw new IllegalArgumentException("重複しています");
        return new User(userCode, name);
    }

    public static boolean isDuplicationUserCode(UserQueryRepository qr, String user_id, UserCode userCode){
        User user = null; //qr.findByUserCode(userCode);
        if (null == user) return false;
        return !user._id().equals(user_id);
    }

    public static void tradeItem(User from, User to, Item item, int quantity){
        if (1 <= from.numOfHoldings(item._id()))
            throw new IllegalArgumentException();
        from.giveItems(item._id(), quantity);
        to.takeItems(item._id(), quantity);
    }

    public static void changeUserCode(UserQueryRepository qr, User user, UserCode newUserCode){
        if (isDuplicationUserCode(qr, user._id(), newUserCode)) throw new IllegalArgumentException("重複しています");
//        user.changeUserCode(newUserCode);
    }
}
