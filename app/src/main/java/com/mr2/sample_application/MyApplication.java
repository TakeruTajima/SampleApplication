package com.mr2.sample_application;

import android.app.Application;

import com.mr2.sample_app_infra.room_database.MyDatabase;

public class MyApplication extends Application {
    public SampleApplicationComponent component;
    public MyDatabase db;
    @Override
    public void onCreate() {
        super.onCreate();
        setUp();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        component = null;
    }

    private void setUp(){
        component = DaggerSampleApplicationComponent.builder()
                .sampleApplicationModule(new SampleApplicationModule(this))
                .build();
        db = MyDatabase.getInstance(this.getApplicationContext());
    }

}
