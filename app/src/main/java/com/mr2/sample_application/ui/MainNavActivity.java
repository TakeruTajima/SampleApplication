package com.mr2.sample_application.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.mr2.sample_application.R;

public class MainNavActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activityのレイアウト指定
        setContentView(R.layout.activity_main_nav);

        // Toolbar(ActionBar)のセットアップ
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // FABのセットアップ
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                //　tips:setActionの第2引数で任意のスナックバーのメッセージクリック時のアクションを設定できる
//            }
//        });

        // NavigationDrawerのセットアップ
        DrawerLayout drawer = findViewById(R.id.drawer_layout); // DrawerLayoutを取得して
        mAppBarConfiguration = new AppBarConfiguration.Builder( // AppBarConfigurationをビルド
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                // Passing each menu ID as a set of Ids because each
                // menu should be considered as top level destinations.
                // tips: 最上位のDestination(Fragment)のidを設定。
                // ここで設定したDestinationを起点に画面遷移する。
                // 下位のDestinationではバーガーの代わりに「戻る」ボタンが表示される。
                .setOpenableLayout(drawer)
//                .setDrawerLayout(drawer) //Deprecated
                .build();
        NavigationView navigationView = findViewById(R.id.nav_view); // NavigationViewと
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment); // NavControllerを取得して
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration); // ActionBarと
        NavigationUI.setupWithNavController(navigationView, navController); // ActionBarにNavControllerをセットアップ
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle( // sync drawer　ついでにバーガーアイコン動かしてみる
                        this, drawer, toolbar, R.string.nav_app_bar_open_drawer_description, R.string.navigation_drawer_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState(); // TODO:画面遷移した後動かなくなる　謎
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Toolbar(ActionBar)にOptionMenuが設定されていると呼び出される。
        getMenuInflater().inflate(R.menu.main_nav, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        // UPボタンの制御にNavigationUI.navigateUpによるDestination階層管理を挿し込み
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}