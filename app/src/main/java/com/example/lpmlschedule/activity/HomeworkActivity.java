package com.example.lpmlschedule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.lpmlschedule.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class HomeworkActivity extends AppCompatActivity {
    protected BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.schedule:
                    Intent scheduleActivityIntent = new Intent(HomeworkActivity.this, MainActivity.class);
                    HomeworkActivity.this.startActivity(scheduleActivityIntent);
                    return true;
                case R.id.deals:
                    Intent dealsActivityIntent = new Intent(HomeworkActivity.this, DealActivity.class);
                    HomeworkActivity.this.startActivity(dealsActivityIntent);
                    return true;
                case R.id.homework:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.homework);
    }
}
