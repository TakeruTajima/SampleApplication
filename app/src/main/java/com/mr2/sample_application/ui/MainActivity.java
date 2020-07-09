package com.mr2.sample_application.ui;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mr2.sample_application.R;
import com.mr2.sample_application.ui.parts_register.PartsRegisterFragment;
import com.mr2.sample_application.ui.sample_data_list.SampleDataListFragment;
//import com.mr2.sample_application_ui.BlankFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Activityのレイアウト指定
//        setContentView(R.layout.main_activity);
        setContentView(R.layout.activity_main_nav);
        // Toolbar セットアップ
//        Toolbar toolbar = findViewById(R.id.main_toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // NavigationDrawer セットアップ
        {}

//        if (savedInstanceState == null)
//        {
//            setSampleListFragment();
//            setPartsRegisterFragment();
//        }
    }

    private void setSampleListFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, SampleDataListFragment.newInstance())
//                .addToBackStack("")
                .commit();

        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar){
            actionBar.setTitle("部品リスト");
//            actionBar.setSubtitle("新規登録");
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeButtonEnabled(false);
        }
    }

    private void setPartsRegisterFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PartsRegisterFragment.newInstance())
                .addToBackStack("")
                .commit();

        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar){
            actionBar.setTitle("部品情報");
            actionBar.setSubtitle("新規登録");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    private void setNavigation(){
//        getNavController()
    }
}
