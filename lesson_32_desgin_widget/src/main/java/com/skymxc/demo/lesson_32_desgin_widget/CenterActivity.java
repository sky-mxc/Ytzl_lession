package com.skymxc.demo.lesson_32_desgin_widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

public class CenterActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
        navigationView= (NavigationView) findViewById(R.id.navigation);
        photo=(ImageView)navigationView.getHeaderView(0).findViewById(R.id.photo);
        navigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
        Log.e("CenterActivity", "onCreate: photo="+photo);

        photo.setImageResource(R.mipmap.g);
    }

    private NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener  = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return true;
        }
    };

}
