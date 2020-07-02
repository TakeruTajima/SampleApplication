package com.mr2.sample_app_application;

import androidx.annotation.NonNull;

import com.mr2.sample_app_domain.common.Price;
import com.mr2.sample_app_domain.parts.Parts;
import com.mr2.sample_app_domain.parts.PartsRepository;

public class PartsRegisterApplicationService {
    private PartsRepository repository;

    public PartsRegisterApplicationService(@NonNull PartsRepository repository) {
        this.repository = repository;
    }

    public void registerSampleData(){
        Parts parts = new Parts("name", "model", "maker", new Price(100, "yen"), "unit");
        repository.store(parts);
    }

    public void registerNewParts(String name, String model, String maker, int price, String currency, String unit){
        //ここいらでメーカーと型式の一意性チェック必要

        Parts parts = new Parts(name, model, maker, new Price(price, currency), unit);
        repository.store(parts);
    }

    public String mockTest(int id){
        return "name: " + repository.get(id).getName();
    }

}
