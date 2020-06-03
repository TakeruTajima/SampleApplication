package com.mr2.sample_application.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mr2.sample_application.R;
import com.mr2.sample_application.ui.item_list.ItemListFragment;
import com.mr2.sample_application.ui.main.MainFragment;
import com.mr2.domain.user.User;
import com.mr2.sample_application.ui.sample_data_list.SampleDataListFragment;
//import com.mr2.sample_application_ui.BlankFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            setSampleListFragment();
        }
    }

    private void setMainFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .addToBackStack("")
                .commit();
    }

    private void setItemListFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ItemListFragment.newInstance())
                .addToBackStack("")
                .commit();
    }

    private void setSampleListFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, SampleDataListFragment.newInstance())
                .addToBackStack("")
                .commit();
    }
}
