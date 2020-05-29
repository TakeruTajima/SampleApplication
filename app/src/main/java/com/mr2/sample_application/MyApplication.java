package com.mr2.sample_application;

import android.app.Application;

public class MyApplication extends Application {
    public SampleApplicationComponent component;
    @Override
    public void onCreate() {
        super.onCreate();
        setUpComponent();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        component = null;
    }

    private void setUpComponent(){
        component = DaggerSampleApplicationComponent.builder()
                .sampleApplicationModule(new SampleApplicationModule(this))
                .build();
    }

}
