package com.mr2.sample_application;

import android.app.Application;

import com.mr2.sample_app_infra.room_database.MyDatabase;
import com.mr2.sample_application.ui.parts_register.DaggerPartsRegisterApplicationServiceComponent;
import com.mr2.sample_application.ui.parts_register.PartsRegisterApplicationServiceComponent;
import com.mr2.sample_application.ui.parts_register.PartsRegisterApplicationServiceModule;

public class MyApplication extends Application {
    public SampleApplicationComponent sampleApplicationComponent;
    public PartsRegisterApplicationServiceComponent component;
    public MyDatabase db;
    @Override
    public void onCreate() {
        super.onCreate();
        setUp();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        sampleApplicationComponent = null;
    }

    private void setUp(){
        sampleApplicationComponent = DaggerSampleApplicationComponent.builder()
                .sampleApplicationModule(new SampleApplicationModule(this))
                .build();
        component = DaggerPartsRegisterApplicationServiceComponent.builder()
                .partsRegisterApplicationServiceModule(new PartsRegisterApplicationServiceModule(this))
                .build();
        db = MyDatabase.getInstance(this.getApplicationContext());
    }

}
