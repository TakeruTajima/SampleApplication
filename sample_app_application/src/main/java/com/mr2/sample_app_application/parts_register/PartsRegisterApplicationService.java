package com.mr2.sample_app_application.parts_register;

import androidx.annotation.NonNull;

import com.mr2.sample_app_domain.common.Price;
import com.mr2.sample_app_domain.parts.Parts;
import com.mr2.sample_app_domain.parts.PartsRepository;
import com.mr2.sample_app_domain.parts.PartsService;

import java.util.Optional;

public class PartsRegisterApplicationService {
    private PartsRepository repository;
    private PartsService service;

    public PartsRegisterApplicationService(@NonNull PartsRepository repository) {
        this.repository = repository;
        this.service = new PartsService(repository);
    }

//    public void registerSampleData(){
//        Parts parts = new Parts("name", "model", "maker", new Price(100, "yen"), "unit");
//        repository.store(parts);
//    }

//    public void registerNewParts(String name, String model, String maker, int price, String currency, String unit){
//        Parts parts = new Parts(name, model, maker, new Price(price, currency), unit);
//        //メーカーと型式の一意性チェック
//        if (service.isDuplicated(parts)){
//            throw new IllegalArgumentException("Parts集約のメーカーと型式の組み合わせが重複しています");
//        }else {
//            repository.store(parts);
//        }
//    }

    public void registerNewParts(PartsRegisterDto dto){
        if (!dto.isReady()){
            throw new IllegalArgumentException("項目が足りません");
        }
        Parts parts = new Parts(
                dto.name,
                dto.model,
                dto.maker,
                new Price(dto.value, dto.currency),
                dto.unit);
        //メーカーと型式の一意性チェック　UI層のプレゼンテーションで予め確認してくださいよ
        if (service.isDuplicated(parts)){
            throw new IllegalArgumentException("Parts集約のメーカーと型式の組み合わせが重複しています");
        }else {
            repository.store(parts); // newしてるんだから楽観的排他処理でひっかかりはしない　はず
        }

    }

    public String mockTest(int id){
        Optional<Parts> parts = repository.get(id);
        return parts.map(value -> "name: " + value.getName()).orElse("missing item");
    }

    // 例外について

    // IllegalArgumentException
    //  与えられたパラメータが想定外の場合
    // IllegalStateException
    //  呼び出された側の内部状態が想定外の場合
    // UnsupportedOperationException
    //  本来呼び出されてはいけない処理が呼び出された場合
    // UncheckedIOException
    //  IOException とそのサブクラスをラップして送出したい場合
}
