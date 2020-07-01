package com.mr2.sample_app_domain.common;

import java.util.regex.Pattern;

public abstract class AssertionConcern {

    protected static boolean isEquals(Object anObject1, Object anObject2) {
        return anObject1.equals(anObject2);
    }

    protected static boolean isFalse(boolean aBoolean) {
        return !aBoolean;
    }

    protected static boolean isLengthOf(String aString, int aMaximum) {
        int length = aString.trim().length();
        return length <= aMaximum;
    }

    protected static boolean isLengthOf(String aString, int aMinimum, int aMaximum) {
        int length = aString.trim().length();
        return length >= aMinimum && length <= aMaximum;
    }

    protected static boolean isNotEmpty(String aString) {
        return aString != null && !aString.trim().isEmpty();
    }

    protected static boolean isNotEquals(Object anObject1, Object anObject2) {
        return !anObject1.equals(anObject2);
    }

    protected static boolean isNotNull(Object anObject) {
        return anObject != null;
    }

    protected static boolean isRangeOf(double aValue, double aMinimum, double aMaximum) {
        return !(aValue < aMinimum) && !(aValue > aMaximum);
    }

    protected static boolean isRangeOf(float aValue, float aMinimum, float aMaximum) {
        return !(aValue < aMinimum) && !(aValue > aMaximum);
    }

    protected static boolean isRangeOf(int aValue, int aMinimum, int aMaximum) {
        return aValue >= aMinimum && aValue <= aMaximum;
    }

    protected static boolean isRangeOf(long aValue, long aMinimum, long aMaximum) {
        return aValue >= aMinimum && aValue <= aMaximum;
    }

    protected static boolean isTrue(boolean aBoolean) {
        return aBoolean;
    }

    protected static boolean isAlphanumeric(String aString){
        return Pattern.matches("^[0-9a-zA-Z]+$", aString);
    }

    protected static void guarantee(boolean bool, String message){
        if (!bool) throw new IllegalArgumentException(message);
    }
}
