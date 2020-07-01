package com.mr2.sample_application.ui.item_list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;
import com.mr2.sample_app_infra.observer.ItemHeadlineDto;
import com.mr2.sample_application.MyApplication;

public class ItemListViewModel extends AndroidViewModel {
    @NonNull
    private final MyApplication app;
    public LiveData<PagedList<ItemHeadlineDto>> pagedList;
    public LiveData<PageKeyedDataSource<Integer, ItemHeadlineDto>> dataSourceLiveData;

    public ItemListViewModel(@NonNull Application application) {
        super(application);
        app = (MyApplication) application;
    }

    public void fetchItemList(){
//        new Thread(() ->{
//            DataSource.Factory<Integer, ItemHeadlineDto> factory
//                    = app.component.getObserverService().getItemList();
//            pagedList = new LivePagedListBuilder(factory, 10).build();
//        }).start();
    }
}
