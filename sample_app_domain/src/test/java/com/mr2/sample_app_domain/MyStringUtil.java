package com.mr2.sample_app_domain;

import androidx.annotation.NonNull;

public class MyStringUtil {

    public static String repeatStr(int length, @NonNull String str){
        StringBuilder result = new StringBuilder(str);
        for (int i = 1; i < length; i++) {
            result.append(str);
        }
        return result.toString();
    }
}
