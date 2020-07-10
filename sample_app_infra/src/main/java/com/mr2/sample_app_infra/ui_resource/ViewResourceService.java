package com.mr2.sample_app_infra.ui_resource;

import android.content.Context;

import androidx.paging.DataSource;

import com.mr2.sample_app_infra.room_database.MyDatabase;


public class ViewResourceService {
    private Context context;

    public ViewResourceService(Context context) {
        this.context = context;
    }

    public DataSource.Factory<Integer, UserHeadlineDto> getUserHeadlinePaging(){
        return MyDatabase.getInstance(context).listDataSourceDao().getUserListSource();
    }

    public DataSource.Factory<Integer, ItemHeadlineDto> getItemList(){
        return MyDatabase.getInstance(context).listDataSourceDao().getItemListSource();
    }
}
