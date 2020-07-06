package com.mr2.sample_application.ui.parts_register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mr2.sample_app_domain.parts.Parts;
import com.mr2.sample_app_infra.observer.MakerListDto;
import com.mr2.sample_application.Executors;
import com.mr2.sample_application.MyApplication;

import java.util.List;

public class PartsRegisterViewModel extends AndroidViewModel {
    private MyApplication app;
    public LiveData<List<MakerListDto>> makerList;
    public MutableLiveData<String> maker = new MutableLiveData<>("");
    public MutableLiveData<String> model = new MutableLiveData<>("");
    public MutableLiveData<Boolean> doneBaseInfo = new MutableLiveData<>(false);

    public PartsRegisterViewModel(@NonNull Application application) {
        super(application);
        app = (MyApplication) application;

        Executors.ioThread(()->{
            makerList = app.db.partsDao().getMakerList();
        });
    }

    public void onChangedMaker(String maker){
        if (Parts.validateMaker(maker) && Parts.validateModel(model.getValue())){
            doneBaseInfo.postValue(true);
        }else doneBaseInfo.postValue(false);
    }
    public void onChangedModel(String model){
        if (Parts.validateModel(model) && Parts.validateMaker(maker.getValue())){
            doneBaseInfo.postValue(true);
        }else doneBaseInfo.postValue(false);
    }
}
