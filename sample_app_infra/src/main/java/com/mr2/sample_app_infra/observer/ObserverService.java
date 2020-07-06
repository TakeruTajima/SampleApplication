package com.mr2.sample_app_infra.observer;

import android.content.Context;

import androidx.paging.DataSource;

import com.mr2.sample_app_infra.room_database.MyDatabase;


public class ObserverService {
    private Context context;

    public ObserverService(Context context) {
        this.context = context;
    }

    public DataSource.Factory<Integer, UserHeadlineDto> getUserHeadlinePaging(){
        return MyDatabase.getInstance(context).listDataSourceDao().getUserListSource();
    }

    public DataSource.Factory<Integer, ItemHeadlineDto> getItemList(){
        return MyDatabase.getInstance(context).listDataSourceDao().getItemListSource();
    }
}
