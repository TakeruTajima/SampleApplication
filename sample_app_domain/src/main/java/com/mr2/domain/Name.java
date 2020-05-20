package com.mr2.domain;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

public class Name {
    @NotNull private String firstName;
    @Nullable private String middleName;
    @Nullable private String familyName;

    public static int invariantInspection(@NotNull String firstName, @Nullable String middleName, @Nullable String familyName){

        return -1;
    }

    public Name(@NotNull String firstName, @Nullable String middleName, @Nullable String familyName) {
        if (-1 != invariantInspection(firstName, middleName, familyName))
            throw new IllegalArgumentException("不変条件に違反しています。");
        this.firstName = firstName;
        this.middleName = (null != middleName && 0 != middleName.length() ? middleName : null);
        this.familyName = (null != familyName && 0 != familyName.length() ? familyName : null);
    }

    public String firstName() {
        return firstName;
    }

    public String middleName() {
        return middleName;
    }

    public String lastName() {
        return familyName;
    }

    public String getFullNameFamilyNameLast() {
        if (null == familyName && null == middleName) return firstName;
        if (null == middleName) return firstName + " " + familyName;
        return firstName + " " + middleName + " " + familyName;
    }

    public String getFullNameFamilyNameFirst(){
        if (null == familyName && null == middleName) return firstName;
        if (null == middleName) return familyName + " " + firstName;
        return familyName + " " + middleName + " " + firstName;
    }
}
