package com.mr2.sample_app_application.parts_register;

public class PartsRegisterDto {
    public String name;
    public String model;
    public String maker;
    public int value;
    public String currency;
    public String unit;

    public boolean isReady(){
        return null != name &&
                null != model &&
                null != maker &&
                0 <= value &&
                null != currency &&
                null != unit;
    }

    public PartsRegisterDto setName(String name) {
        this.name = name;
        return this;
    }

    public PartsRegisterDto setModel(String model) {
        this.model = model;
        return this;
    }

    public PartsRegisterDto setMaker(String maker) {
        this.maker = maker;
        return this;
    }

    public PartsRegisterDto setValue(int value) {
        this.value = value;
        return this;
    }

    public PartsRegisterDto setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public PartsRegisterDto setUnit(String unit) {
        this.unit = unit;
        return this;
    }
}
