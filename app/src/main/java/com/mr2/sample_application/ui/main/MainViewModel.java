package com.mr2.sample_application.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mr2.sample_app_application.SampleApplicationService;
import com.mr2.sample_application.MyApplication;

public class MainViewModel extends AndroidViewModel {
//    private Application app;
    private SampleApplicationService service;
    private
    public LiveData<String>

    public MainViewModel(@NonNull Application app) {
        super(app);
//        this.app = app;
        this.service = ((MyApplication)app).component.getSampleApplicationService();
    }
    // TODO: Implement the ViewModel
//    MutableLiveData<List>

    public void fetchDefaultText(){

    }
}
