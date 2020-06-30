package com.mr2.sample_app_domain.parts;

import androidx.annotation.NonNull;

import com.mr2.sample_app_domain.common.AbstractEntity;
import com.mr2.sample_app_domain.common.Price;

import java.util.Objects;

public class Parts extends AbstractEntity {

    //新規
    public Parts(String name, String model, String maker, Price value, String unit) {
        super(INITIAL_VERSION, INITIAL_ID); // uuid???
        guarantee(validateName(name));
        guarantee(validateModel(model));
        guarantee(validateMaker(maker));
        guarantee(validateValue(value));
        guarantee(validateUnit(unit));
        this.name = name;
        this.model = model;
        this.maker = maker;
        this.value = value;
        this.unit = unit;
    }

    //読み出し
    public Parts(int version, int id, String name, String model, String maker, Price value, String unit) {
        super(version, id);
        this.name = name;
        this.model = model;
        this.maker = maker;
        this.value = value;
        this.unit = unit;
    }

    //メンバと不変条件
    private String name;  // 品名
    public static boolean validateName(String name){
        if (!isNotEmpty(name)) return false;
        return isLengthOf(name, 100);
    }

    private String model; // 品番
    public static boolean validateModel(String model){
        if (!isNotEmpty(model)) return false;
        return isLengthOf(model, 999);
    }

    private String maker; // メーカー名
    public static boolean validateMaker(String maker){
        if (!isNotEmpty(maker)) return false;
        return isLengthOf(maker, 100);
    }

    private Price value;  // 管理単価(資産価値)
    // ValueObjectは自身に不変条件を持つのでEntity内でのチェックは無し
    public static boolean validateValue(Price value){ return true; }

    private String unit;  // 管理単位
    public static boolean validateUnit(String unit){
        if (!isNotEmpty(unit)) return false;
        return isLengthOf(unit, 10);
    }

    //品名を変更する
    public void changeName(@NonNull String newName){
        guarantee(validateName(name));
        this.name = newName;
    }

    //単価を変更する
    public void changeValue(@NonNull Price newValue){
        guarantee(validateValue(newValue));
        this.value = newValue;
    }

    //単位を変更する
    public void changeUnit(@NonNull String newUnit){
        guarantee(validateUnit(newUnit));
        this.unit = newUnit;
    }

    /* 以下ゲッター */
    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getMaker() {
        return maker;
    }

    public Price getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parts)) return false;
        Parts parts = (Parts) o;
        return Objects.equals(name, parts.name) &&
                Objects.equals(model, parts.model) &&
                Objects.equals(maker, parts.maker) &&
                Objects.equals(value, parts.value) &&
                Objects.equals(unit, parts.unit) &&
                Objects.equals(id, parts.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, model, maker, value, unit);
    }
}
