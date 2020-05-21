package com.mr2.domain.user;

import com.sun.istack.internal.NotNull;

public class UserCode {
    @NotNull private final String value;

    public static int invariantInspection(@NotNull String value){
        if (null == value || 0 == value.length() || 20 <= value.length()) return 1;
        return -1;
    }

    public UserCode(@NotNull String value) {
        this.value = value;
        if (-1 != invariantInspection(this.value))
            throw new IllegalArgumentException("");
    }

    public String value(){
        return value;
    }
}
