package com.mr2.sample_application;

import com.mr2.domain.item.ItemCommandRepository;
import com.mr2.domain.item.ItemQueryRepository;
import com.mr2.domain.user.UserCommandRepository;
import com.mr2.domain.user.UserQueryRepository;
import com.mr2.sample_app_application.SampleApplicationService;
import com.mr2.sample_app_application.SampleApplicationServiceImpl;
import com.mr2.sample_app_infra.repositories.ItemCommandRepositoryImpl;
import com.mr2.sample_app_infra.repositories.ItemQueryRepositoryImpl;
import com.mr2.sample_app_infra.repositories.UserCommandRepositoryImpl;
import com.mr2.sample_app_infra.repositories.UserQueryRepositoryImpl;
import com.mr2.sample_app_infra.room_database.MyDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class SampleApplicationModule {
    private final MyApplication app;
    private final MyDatabase db;

    public SampleApplicationModule(MyApplication app) {
        this.app = app;
        this.db = MyDatabase.getInstance(app.getApplicationContext());
    }

//    @Provides
//    Context provideApplicationContext(){
//        return app.getApplicationContext();
//    }

    @Provides
    ItemCommandRepository provideItemCommandRepository(){
        return new ItemCommandRepositoryImpl(db);
    }

    @Provides
    ItemQueryRepository provideItemQueryRepository(){
        return new ItemQueryRepositoryImpl(db);
    }

    @Provides
    UserCommandRepository provideUserCommandRepository(){
        return new UserCommandRepositoryImpl(db);
    }

    @Provides
    UserQueryRepository provideUserQueryRepository(){
        return new UserQueryRepositoryImpl(db);
    }

    //interfaceに対してDIしたい場合、実装が一つしかないとしてもDaggerは動いてくれない。
    //どのinterfaceに対しどの実装を使うのか明示する必要がある。
    @Provides
    SampleApplicationService provideSampleApplicationService(){
        return new SampleApplicationServiceImpl(
                provideUserQueryRepository(),
                provideUserCommandRepository(),
                provideItemQueryRepository(),
                provideItemCommandRepository());
    }
}
