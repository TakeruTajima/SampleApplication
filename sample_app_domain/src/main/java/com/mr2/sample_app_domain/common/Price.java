package com.mr2.sample_app_domain.common;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Price extends ValueObject{
    public final float value; //金額
    public static boolean validateValue(float value){
        return isRangeOf(value, 0, 999_999_999);
    }

    public final String currency; //通貨
    public static boolean validateCurrency(String currency){
        return isLengthOf(currency, 1, 10);
    }

    public Price(float value, @NonNull String currency) {
        guarantee(validateValue(value));
        guarantee(validateCurrency(currency));
        this.value = value;
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;
        Price price = (Price) o;
        return Float.compare(price.value, value) == 0 &&
                Objects.equals(currency, price.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }
}
