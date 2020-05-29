package com.mr2.sample_application;

import com.mr2.sample_app_application.SampleApplicationService;

import dagger.Component;

@Component (modules = {SampleApplicationModule.class})
public interface SampleApplicationComponent{
    SampleApplicationService getSampleApplicationService();
}
