package com.mr2.domain.user;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

public class Name {
    @NotNull private final String firstName;
    @NotNull private final String middleName;
    @NotNull private final String familyName;

    public static int invariantInspection(@NotNull String firstName, @NotNull String middleName, @NotNull String familyName){
        if (0 == firstName.length()) return 1;
        if (0 == familyName.length() && 0 != middleName.length()) return 2;
        return -1;
    }

    public Name(@NotNull String firstName, @NotNull String middleName, @NotNull String familyName) throws IllegalArgumentException{
        this.firstName = (null != firstName ? firstName : "");
        this.middleName = (null != middleName ? middleName : "");
        this.familyName = (null != familyName ? familyName : "");
        if (-1 != invariantInspection(this.firstName, this.middleName, this.familyName))
            throw new IllegalArgumentException("不変条件に違反しています。");
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
