package com.sample.infam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;

public class ParentMain extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.nav_open_drawer,R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        com.google.android.material.navigation.NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();
    Log.d(this.getClass().getName(),"현재 선택한 이유는 = "+id);

    switch (id){
        case R.id.mark:

            break;
        case R.id.record:
            break;
        case R.id.logout:
            break;
        case R.id.bye:
            break;
    }


    return false;
    }
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

    }
}