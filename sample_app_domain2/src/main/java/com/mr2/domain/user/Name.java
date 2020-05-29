package com.mr2.domain.user;


public class Name {
    private final String firstName;
    private final String middleName;
    private final String familyName;

    public static int invariantInspection(String firstName, String middleName, String familyName){
        if (null == firstName || null == middleName || null == familyName) return 1;
        if (0 == firstName.length()) return 2;
        if (0 == familyName.length() && 0 != middleName.length()) return 3;
        return -1;
    }

    public Name(String firstName,  String middleName, String familyName){
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
