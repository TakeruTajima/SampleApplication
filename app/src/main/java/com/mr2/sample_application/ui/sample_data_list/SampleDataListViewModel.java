package com.mr2.sample_application.ui.sample_data_list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.mr2.sample_app_infra.room_database.sample_list_data.SampleListData;
import com.mr2.sample_application.MyApplication;

public class SampleDataListViewModel extends AndroidViewModel {
    private final MyApplication app;
    public LiveData<PagedList<SampleListData>> listLiveData;
    public MutableLiveData<Integer> listSize;

    public SampleDataListViewModel(@NonNull Application application) {
        super(application);
        app = (MyApplication) application;
    }

    public PagedList<SampleListData> getPagedList(){
        return listLiveData.getValue();
    }

    public String getListSize(){
        return listSize.getValue() + "(s) records.";
    }

    public void fetchList(LifecycleOwner lifecycleOwner, Observer<PagedList<SampleListData>> observer){
        new Thread(()->{
            DataSource.Factory<Integer, SampleListData> factory = app.db.sampleDao().getPagedData();
            listLiveData = new LivePagedListBuilder<>(factory, 50).build();
//            System.out.println("//////////////////////////////////////////LivePagedListBuilder<>(factory, 50).build()::: completed.");
//            if (null == listLiveData)
//                System.out.println("null == listLiveData");
//            if (null == listLiveData.getValue())
//                System.out.println("null == listLiveData.getValue()");
//            System.out.println("end.");
            listLiveData.observe(lifecycleOwner, observer);
            //Cannot invoke observe on a background thread
        }).start();
    }
}
