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

import com.mr2.sample_app_infra.room_database.MyDatabase;
import com.mr2.sample_app_infra.room_database.sample_list_data.SampleListData;
import com.mr2.sample_application.Executors;
import com.mr2.sample_application.MyApplication;

public class SampleDataListViewModel extends AndroidViewModel {
    private final MyApplication app;
    public LiveData<PagedList<SampleListData>> listLiveData /* = new MutableLiveData<>()*/;
    public MutableLiveData<Boolean> isLoadFinished = new MutableLiveData<>(false);
    public MutableLiveData<Integer> liveListSize = new MutableLiveData<>();

    public SampleDataListViewModel(@NonNull Application application) {
        super(application);
        app = (MyApplication) application;
        /* Config example */
//        PagedList.Config c = new PagedList.Config.Builder()
//                .setPageSize(30)
//                .setMaxSize(90)
//                .setEnablePlaceholders(false)
////                .setPrefetchDistance()
//                .build();
        Executors.ioThread(()->{
            listLiveData = new LivePagedListBuilder<>(app.db.sampleDao().getPagedData(), 30).build();
            isLoadFinished.postValue(true);
        });
    }

    public void addItem(){
        Executors.ioThread(()->{
            app.db.sampleDao().insert(
                    new SampleListData("additional item")
            );
        });
    }

    public void addItem(String itemName){
        Executors.ioThread(()->{
            if (null == itemName || 0 == itemName.length()) return;
            app.db.sampleDao().insert(
                    new SampleListData(itemName)
            );
        });
    }

    //　以下はLiveDataから直接参照してないのでObserveできない？
//    public String getIsLoadFinished(){ return (null == isLoadFinished.getValue()) ? null : isLoadFinished.getValue().toString(); }
}
