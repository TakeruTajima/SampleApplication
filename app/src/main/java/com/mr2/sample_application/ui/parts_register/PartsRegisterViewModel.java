package com.mr2.sample_application.ui.parts_register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mr2.sample_app_domain.parts.Parts;
import com.mr2.sample_app_infra.ui_resource.parts_register.MakerListDto;
import com.mr2.sample_app_infra.ui_resource.parts_register.ModelListDto;
import com.mr2.sample_app_infra.ui_resource.parts_register.UnitListDto;
import com.mr2.sample_application.Executors;
import com.mr2.sample_application.MyApplication;

import java.util.List;

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
    public LiveData<List<UnitListDto>> unitList;
    public MutableLiveData<Float> price = new MutableLiveData<>((float) 0);
    public MutableLiveData<Boolean> isValidPriceInfo = new MutableLiveData<>(false);


    public PartsRegisterViewModel(@NonNull Application application) {
        super(application);
        app = (MyApplication) application;

        // suggest data
        Executors.ioThread(()->{
            makerList = app.db.partsDao().getMakerList();
            modelList = app.db.partsDao().getModelList(maker.getValue());
            unitList = app.db.partsDao().getUnitList();
        });
    }

    public static final String CURRENT_CURRENCY = "円";

    public void onEdit() {
        isValidCoreInfo.postValue(Parts.validateMaker(maker.getValue()) && Parts.validateModel(maker.getValue()));
        isValidPartsName.postValue(Parts.validateName(name.getValue()));
        float p = 0;
        if (price.getValue() != null) p = price.getValue();
        isValidPriceInfo.postValue(Parts.validateUnit(unit.getValue()) && Parts.validateValue(p, CURRENT_CURRENCY));
    }

    public void onMakerEdit(){
        modelList = app.db.partsDao().getModelList(maker.getValue());
    }

    public void onSaveClicked() {

    }
}
