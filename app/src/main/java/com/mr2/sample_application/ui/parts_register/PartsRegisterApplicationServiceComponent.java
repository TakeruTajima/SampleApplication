package com.mr2.sample_application.ui.parts_register;

import com.mr2.sample_app_application.parts_register.PartsRegisterApplicationService;

import dagger.Component;

@Component (modules = {PartsRegisterApplicationServiceModule.class})
public interface PartsRegisterApplicationServiceComponent {
    PartsRegisterApplicationService getPartsRegisterApplicationService();
}
