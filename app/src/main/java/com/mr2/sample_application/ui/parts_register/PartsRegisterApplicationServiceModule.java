package com.mr2.sample_application.ui.parts_register;

import com.mr2.sample_app_application.parts_register.PartsRegisterApplicationService;
import com.mr2.sample_app_domain.parts.PartsRepository;
import com.mr2.sample_app_infra.repositories.PartsRepositoryImpl;
import com.mr2.sample_app_infra.room_database.MyDatabase;
import com.mr2.sample_application.MyApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class PartsRegisterApplicationServiceModule {
    private final MyApplication app;
    private final MyDatabase db;

    public PartsRegisterApplicationServiceModule(MyApplication app) {
        this.app = app;
        this.db = MyDatabase.getInstance(app.getApplicationContext());
    }

    @Provides
    public PartsRepository partsRepository(){
        return new PartsRepositoryImpl(db);
    }

    @Provides
    public PartsRegisterApplicationService partsRegisterApplicationService(){
        return new PartsRegisterApplicationService(partsRepository());
    }
}
