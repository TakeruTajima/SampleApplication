package com.mr2.sample_application;

import com.mr2.sample_app_application.SampleApplicationService;
import com.mr2.sample_app_infra.ui_resource.ViewResourceService;

import dagger.Component;

@Component (modules = {SampleApplicationModule.class})
public interface SampleApplicationComponent{
    SampleApplicationService getSampleApplicationService();
    ViewResourceService getObserverService();
}
