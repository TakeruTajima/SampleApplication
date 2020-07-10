package com.mr2.sample_application.ui.parts_register;

import android.app.Application;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mr2.sample_app_domain.parts.Parts;
import com.mr2.sample_app_infra.room_database.parts.PartsEntity;
import com.mr2.sample_app_infra.ui_resource.SingleStringListResource;
import com.mr2.sample_app_infra.ui_resource.parts_register.MakerListDto;
import com.mr2.sample_app_infra.ui_resource.parts_register.ModelListDto;
import com.mr2.sample_app_infra.ui_resource.parts_register.UnitListDto;
import com.mr2.sample_application.Executors;
import com.mr2.sample_application.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PartsRegisterViewModel extends AndroidViewModel {
    private MyApplication app;

    // core info
    public MutableLiveData<String> maker = new MutableLiveData<>("");
    public LiveData<List<MakerListDto>> makerList;
    public MutableLiveData<String> model = new MutableLiveData<>("");
    public LiveData<List<ModelListDto>> modelList;
    public MutableLiveData<Boolean> isValidCoreInfo = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isDuplicate = new MutableLiveData<>(false);

    // info 1
    public MutableLiveData<String> name = new MutableLiveData<>("");
    public MutableLiveData<Boolean> isValidPartsName = new MutableLiveData<>(false);
    // info 2
    public MutableLiveData<String> unit = new MutableLiveData<>("");
    public LiveData<List<SingleStringListResource>> unitList;
    public MutableLiveData<Float> price = new MutableLiveData<>((float) 0);
    public MutableLiveData<Boolean> isValidPriceInfo = new MutableLiveData<>(false);


    LiveData<List<PartsEntity>> l;
    public PartsRegisterViewModel(@NonNull Application application) {
        super(application);
        app = (MyApplication) application;

//        setDefaultData();

        // suggest data
        Executors.ioThread(()->{
//            l = app.db.partsDao().findAll();
            unitList = app.db.partsDao().getUnitList();
            makerList = app.db.partsDao().getMakerList();
            modelList = app.db.partsDao().getModelList(maker.getValue());
            System.out.println("load completed.");
        });
//        Executors.ioThread(() -> l = app.db.partsDao().findAll());
//        Executors.ioThread(()-> outState(app.db.partsDao().allList()));
    }

    void setDefaultData(){
        for (int i = 0; i < 30; i++) {
            int m = i % 3;
            PartsEntity e = new PartsEntity(
                    0,
                    0,
                    "品名" + i,
                    "model" + i,
                    "maker" + m,
                    100.0f * i,
                    "yen",
                    "U:" + m
            );
            Executors.ioThread(()-> app.db.partsDao().insert(e));
        }
    }

    public void outState(List<PartsEntity> partsEntity){
        if (null != partsEntity) {
            for (PartsEntity entity : partsEntity) {
                System.out.println(
                        "id:" + entity.id + "\n" +
                                "ver:" + entity.version + "\n" +
                                "maker:" + entity.maker + "\n" +
                                "model:" + entity.model + "\n" +
                                "name:" + entity.name + "\n" +
                                "unit:" + entity.unit + "\n" +
                                "price:" + entity.price_value + " " + entity.price_currency + "\n");
            }
        }else {
            System.out.println("partsEntityList is null");
        }
    }

    public static final String CURRENT_CURRENCY = "円";

    public void onEdit() {
        modelList = app.db.partsDao().getModelList(maker.getValue());
        isDuplicate.postValue(checkDup());
        isValidCoreInfo.postValue(Parts.validateMaker(maker.getValue()) && Parts.validateModel(model.getValue()));
        isValidPartsName.postValue(Parts.validateName(name.getValue()));
        float p = (price.getValue() == null ? 0 : price.getValue());
        isValidPriceInfo.postValue(Parts.validateUnit(unit.getValue()) && Parts.validateValue(p, CURRENT_CURRENCY));
    }

    private boolean checkDup(){
        String m = model.getValue();
        final List<ModelListDto> value = modelList.getValue();
        if (null == value) return false;
        for (ModelListDto modelListDto : value) {
            if (null != modelListDto && modelListDto.name.equals(m)){
                //duplicated
                return true;
            }
        }
        return false;
    }

    public void onSaveClicked() {
        boolean coreInfo = false;
        if (isValidCoreInfo.getValue() != null) coreInfo = isValidCoreInfo.getValue();
        boolean partsName = false;
        if (isValidPartsName.getValue() != null) partsName = isValidPartsName.getValue();
        boolean priceInfo = false;
        if (isValidPriceInfo.getValue() != null) priceInfo = isValidPriceInfo.getValue();
        if(coreInfo && partsName && priceInfo){
            String s = "maker=" + maker.getValue() + "\n" +
                    " model=" + model.getValue() + "\n" +
                    " name=" + name.getValue() + "\n" +
                    " price=" + price.getValue() + CURRENT_CURRENCY + "/" + unit.getValue();
            System.out.println(s);
        }else {
            System.out.println("is not valid.");
        }
//        outState(l.getValue());
    }
}
