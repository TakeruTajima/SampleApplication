package com.mr2.sample_application.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mr2.sample_application.R;
import com.mr2.sample_application.ui.main.MainFragment;
import com.mr2.domain.User;
//import com.mr2.sample_application_ui.BlankFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }

    public void setBlankFragment(){
        User c = new User();
    }
}
