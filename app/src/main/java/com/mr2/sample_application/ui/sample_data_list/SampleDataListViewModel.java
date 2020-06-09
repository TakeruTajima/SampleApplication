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
    public DataSource.Factory<Integer, SampleListData> factory;
    public MutableLiveData<Boolean> isLoadFinished = new MutableLiveData<>(false);

    public SampleDataListViewModel(@NonNull Application application) {
        super(application);
        app = (MyApplication) application;
    }

    public PagedList<SampleListData> getPagedList(){
        return listLiveData.getValue();
    }

    public void fetchList(){
        Executors.ioThread(()->{
            factory = app.db.sampleDao().getPagedData();
            PagedList.Config c = new PagedList.Config.Builder()
                    .setPageSize(30)
                    .setMaxSize(90)
                    .build();
            listLiveData = new LivePagedListBuilder<>(factory, 30).build();
            //読み込みなどの作業はすぐには行われません。最初のPagedListの作成は、LiveDataが観察されるまで延期されます。
            isLoadFinished.postValue(true);
            //非同期なのでObserveするタイミングを通知
        });
    }
}
