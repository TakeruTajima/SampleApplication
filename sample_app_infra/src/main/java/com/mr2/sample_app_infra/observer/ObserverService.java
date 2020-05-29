package com.mr2.sample_app_infra.observer;

import android.content.Context;

import com.mr2.sample_app_infra.room_database.MyDatabase;

import java.util.List;

public class ObserverService {
    List<UserHeadlineDto> getUserHeadline(Context context){
        return MyDatabase.getInstance(context).userListDao().getSubset();
    }
}
