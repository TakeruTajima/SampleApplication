package com.mr2.domain.user;
public class UserCode {

    private final String value;

    public static int invariantInspection( String value){
        if (0 == value.length() || 20 <= value.length()) return 1;
        return -1;
    }

    public UserCode( String value) {
        this.value = value;
        if (-1 != invariantInspection(this.value))
            throw new IllegalArgumentException("");
    }

    public String value(){
        return value;
    }
}
