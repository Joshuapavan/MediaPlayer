package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView navbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavBar();

    }
    void initNavBar(){
        navbar = findViewById(R.id.navBar);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity,new video_Fragment()).commit();
        navbar.setSelectedItemId(R.id.video);

        navbar.setOnNavigationItemSelectedListener(item -> {

            Fragment fragment = null;
            switch (item.getItemId()){
                case(R.id.audio):
                    fragment = new audio_Fragment();
                    break;
                case(R.id.video):
                    fragment = new video_Fragment();
                    break;
            }
            assert fragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity,fragment).commit();
            return true;
        });
    }
}