package com.mr2.sample_application.ui.sample_data_list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
//import androidx.paging.toLiveData; //kotlin拡張機能につき

import com.mr2.sample_app_infra.room_database.sample_list_data.SampleListData;
import com.mr2.sample_application.Executors;
import com.mr2.sample_application.MyApplication;

public class SampleDataListViewModel extends AndroidViewModel {
    private final MyApplication app;
    public LiveData<PagedList<SampleListData>> listLiveData;
    public MutableLiveData<Integer> listSize;
    public DataSource.Factory<Integer, SampleListData> factory;
    public MutableLiveData<Boolean> isLoadFinished = new MutableLiveData<>(false);

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

    public void fetchList(){
        Executors.ioThread(()->{
            factory = app.db.sampleDao().getPagedData();
            listLiveData = new LivePagedListBuilder<>(factory, 50).build();
            isLoadFinished.postValue(true);
        });


        Executors.ioThreadForResult(() -> {
            String threadName2 = Thread.currentThread().getName();
            System.out.println("thread name: " + threadName2 + " in io thread"); //io thread
        }, () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("thread name: " + threadName + " in a result"); //main thread
        });
//        new Thread(()->{
//            DataSource.Factory<Integer, SampleListData> factory = app.db.sampleDao().getPagedData();
//            listLiveData = new LivePagedListBuilder<>(factory, 50).build();
//
//            System.out.println("//////////////////////////////////////////LivePagedListBuilder<>(factory, 50).build()::: completed.");
//            if (null == listLiveData)
//                System.out.println("null == listLiveData");
//            if (null == listLiveData.getValue())
//                System.out.println("null == listLiveData.getValue()");
//            System.out.println("//////////////////////////////////////////end.");

//            listLiveData.observe(lifecycleOwner, observer);
            //Cannot invoke observe on a background thread
//        }).start();
    }
}
