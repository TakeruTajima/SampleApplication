package com.mr2.domain.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class User {
    private final String _id;
    private UserCode userCode;
    private Name name;
    private Map<String, Integer> holdingItems;

    User( UserCode userCode,  Name name) {
        if (null == userCode || null == name) throw new IllegalArgumentException();
        this._id = UUID.randomUUID().toString();
//        this.userCode = userCode;
//        this.name = name;
        this.holdingItems = new HashMap<>();
    }

    public User(String _id,  UserCode userCode,  Name name,  Map<String, Integer> holdingItems) {
        this._id = _id;
//        this.userCode = userCode;
//        this.name = name;
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

    public Map<String,Integer> holdingItems(){
        Map<String,Integer> items = new HashMap<>(holdingItems.size());
        items.putAll(holdingItems);
        return items;
    }

    public List<String> itemList(){
        return new ArrayList<>(holdingItems.keySet());
    }

    public int numOfHoldings(String item_id){
        Integer quantity = holdingItems.get(item_id);
        if (null == quantity) quantity = 0;
        else if (-1 >= quantity) throw new IllegalStateException("");
        return quantity;
    }

    public void takeItems( String item_id, int quantity){
        if (0 >= quantity || null== item_id) throw new IllegalArgumentException("");
        if (1 <= numOfHoldings(item_id)){
            holdingItems.replace(item_id, numOfHoldings(item_id) + quantity);
        }else {
            holdingItems.put(item_id, quantity);
        }
    }

    public void giveItems( String item_id, int quantity){
        if (0 >= quantity) throw new IllegalArgumentException("");
        if (0 == numOfHoldings(item_id)) return;
        else if (quantity > numOfHoldings(item_id)) throw new IllegalArgumentException("");
        if (quantity == numOfHoldings(item_id)){
            holdingItems.remove(item_id);
        }else {
            holdingItems.replace(item_id, numOfHoldings(item_id) - quantity);
        }

    }

    //UserCodeは重複が許されず、他の集約が関わってくるので、この操作はドメインサービスの責務に該当する。
    void changeUserCode( UserCode newUserCode){
//        this.userCode = newUserCode;
    }

    public void changeName( Name name){
//        this.name = name;
    }

}
