package com.mr2.sample_application.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mr2.sample_application.R;
import com.mr2.sample_application.ui.parts_register.PartsRegisterFragment;
import com.mr2.sample_application.ui.sample_data_list.SampleDataListFragment;
//import com.mr2.sample_application_ui.BlankFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            setPartsRegisterFragment();
        }
    }

    private void setSampleListFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, SampleDataListFragment.newInstance())
                .addToBackStack("")
                .commit();
    }

    private void setPartsRegisterFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PartsRegisterFragment.newInstance())
                .addToBackStack("")
                .commit();
    }
}
