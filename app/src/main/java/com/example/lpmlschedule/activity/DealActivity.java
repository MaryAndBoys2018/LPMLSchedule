package com.example.lpmlschedule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.lpmlschedule.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class DealActivity extends AppCompatActivity {
    protected BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.schedule:
                    Intent scheduleActivityIntent = new Intent(DealActivity.this, MainActivity.class);
                    DealActivity.this.startActivity(scheduleActivityIntent);
                    return true;
                case R.id.deals:
                    return true;
                case R.id.homework:
                    Intent homeworkActivityIntent = new Intent(DealActivity.this, HomeworkActivity.class);
                    DealActivity.this.startActivity(homeworkActivityIntent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);

        navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.deals);
    }
}
