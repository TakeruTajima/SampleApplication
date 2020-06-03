package com.mr2.sample_application.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.mr2.sample_app_application.SampleApplicationService;
import com.mr2.sample_app_infra.observer.ObserverService;
import com.mr2.sample_app_infra.observer.UserHeadlineDto;
import com.mr2.sample_application.MyApplication;

public class MainViewModel extends AndroidViewModel {
    private SampleApplicationService service;
    private ObserverService observerService;
    public MutableLiveData<String> liveDataText;
    public DataSource.Factory<Integer, UserHeadlineDto> pagingList;


    public MainViewModel(@NonNull Application app) {
        super(app);
//        this.app = app;
        this.service = ((MyApplication)app).component.getSampleApplicationService();
        observerService = new ObserverService(app.getApplicationContext());
        liveDataText = new MutableLiveData<>();
    }
    // TODO: Implement the ViewModel
//    MutableLiveData<List>

    public void serviceTest(){
        liveDataText.postValue(service.getSampleText());
    }

    public void pagingList(){
        pagingList = observerService.getUserHeadlinePaging();
    }
}
