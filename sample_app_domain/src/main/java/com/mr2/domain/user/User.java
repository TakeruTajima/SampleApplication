package com.mr2.domain.user;

import com.mr2.domain.item.Item;
import com.sun.istack.internal.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {
    @NotNull private final String _id;
    @NotNull private UserCode userCode;
    @NotNull private Name name;
    private Map<Item, Integer> holdingItems;

    User(@NotNull UserCode userCode, @NotNull Name name) {
        this._id = UUID.randomUUID().toString();
        this.userCode = userCode;
        this.name = name;
        this.holdingItems = new HashMap<>();
    }

    User(@NotNull String _id, @NotNull UserCode userCode, @NotNull Name name, @NotNull Map<Item, Integer> holdingItems) {
        this._id = _id;
        this.userCode = userCode;
        this.name = name;
        this.holdingItems = holdingItems;
    }

    public String _id(){
        return _id;
    }

    public UserCode userCode(){
        return userCode;
    }

    public Name name(){
        return name;
    }

    public int numOfHoldings(Item item){
        Integer quantity = holdingItems.get(item);
        if (null == quantity) quantity = 0;
        else if (-1 >= quantity) throw new IllegalStateException("");
        return quantity;
    }

    public void getItems(@NotNull Item item, int quantity){
        if (0 >= quantity) throw new IllegalArgumentException("");
        if (1 <= numOfHoldings(item)){
            holdingItems.replace(item, numOfHoldings(item) + quantity);
        }else {
            holdingItems.put(item, quantity);
        }
    }

    public void releaseItems(@NotNull Item item, int quantity){
        if (0 >= quantity) throw new IllegalArgumentException("");
        if (0 == numOfHoldings(item)) return;
        else if (quantity > numOfHoldings(item)) throw new IllegalArgumentException("");
        if (quantity == numOfHoldings(item)){
            holdingItems.remove(item);
        }else {
            holdingItems.replace(item, numOfHoldings(item) - quantity);
        }

    }

    //UserCodeは重複が許されず、他の集約が関わってくるので、この操作はドメインサービスの責務に該当する。
    void changeUserCode(@NotNull UserCode newUserCode){
        this.userCode = newUserCode;
    }

    public void changeName(@NotNull Name name){
        this.name = name;
    }

}
