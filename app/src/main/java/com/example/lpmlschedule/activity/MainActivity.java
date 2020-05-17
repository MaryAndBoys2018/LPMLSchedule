package com.example.lpmlschedule.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.lpmlschedule.R;
import com.example.lpmlschedule.adapter.LessonAdapter;
import com.example.lpmlschedule.model.Lesson;
import com.example.lpmlschedule.model.Time;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView scheduleList;
    private TabLayout tabLayout;

    private List<List<Lesson>> schedule;
    private List<Lesson> currentSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeLessons();

        currentSchedule = new ArrayList<>();
        currentSchedule = schedule.get(0);

        scheduleList = findViewById(R.id.schedule_list);
        final LessonAdapter adapter = new LessonAdapter(this, currentSchedule);
        scheduleList.setAdapter(adapter);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentSchedule.clear();
                currentSchedule.addAll(schedule.get(tab.getPosition()));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initializeLessons() {
        List<Lesson> mondayLessons = new ArrayList<>();
        mondayLessons.add(new Lesson("Математика", new Time(8, 30), "205"));
        mondayLessons.add(new Lesson("Математика", new Time(9, 25), "205"));
        mondayLessons.add(new Lesson("Математика", new Time(10, 20), "205"));
        mondayLessons.add(new Lesson("Математика", new Time(11, 25), "205"));
        mondayLessons.add(new Lesson("Математика", new Time(12, 30), "205"));
        mondayLessons.add(new Lesson("Математика", new Time(13, 35), "205"));

        List<Lesson> tuesdayLessons = new ArrayList<>();
        tuesdayLessons.add(new Lesson("Фізика", new Time(8, 30), "205"));
        tuesdayLessons.add(new Lesson("Фізика", new Time(9, 25), "205"));
        tuesdayLessons.add(new Lesson("Фізика", new Time(10, 20), "205"));
        tuesdayLessons.add(new Lesson("Фізика", new Time(11, 25), "205"));
        tuesdayLessons.add(new Lesson("Фізика", new Time(12, 30), "205"));
        tuesdayLessons.add(new Lesson("Фізика", new Time(13, 35), "205"));

        List<Lesson> wednesdayLessons = new ArrayList<>();
        wednesdayLessons.add(new Lesson("Інформатика", new Time(8, 30), "205"));
        wednesdayLessons.add(new Lesson("Інформатика", new Time(9, 25), "205"));
        wednesdayLessons.add(new Lesson("Інформатика", new Time(10, 20), "205"));
        wednesdayLessons.add(new Lesson("Інформатика", new Time(11, 25), "205"));
        wednesdayLessons.add(new Lesson("Інформатика", new Time(12, 30), "205"));
        wednesdayLessons.add(new Lesson("Інформатика", new Time(13, 35), "205"));

        List<Lesson> thursdayLessons = new ArrayList<>();
        thursdayLessons.add(new Lesson("Математика", new Time(8, 30), "205"));
        thursdayLessons.add(new Lesson("Математика", new Time(9, 25), "205"));
        thursdayLessons.add(new Lesson("Математика", new Time(10, 20), "205"));
        thursdayLessons.add(new Lesson("Математика", new Time(11, 25), "205"));
        thursdayLessons.add(new Lesson("Математика", new Time(12, 30), "205"));
        thursdayLessons.add(new Lesson("Математика", new Time(13, 35), "205"));

        List<Lesson> fridayLessons = new ArrayList<>();
        fridayLessons.add(new Lesson("Фізика", new Time(8, 30), "205"));
        fridayLessons.add(new Lesson("Фізика", new Time(9, 25), "205"));
        fridayLessons.add(new Lesson("Фізика", new Time(10, 20), "205"));
        fridayLessons.add(new Lesson("Фізика", new Time(11, 25), "205"));
        fridayLessons.add(new Lesson("Фізика", new Time(12, 30), "205"));
        fridayLessons.add(new Lesson("Фізика", new Time(13, 35), "205"));

        List<Lesson> saturdayLessons = new ArrayList<>();
        saturdayLessons.add(new Lesson("Англійська мова", new Time(8, 30), "205"));
        saturdayLessons.add(new Lesson("Англійська мова", new Time(9, 25), "205"));
        saturdayLessons.add(new Lesson("Англійська мова", new Time(10, 20), "205"));
        saturdayLessons.add(new Lesson("Англійська мова", new Time(11, 25), "205"));
        saturdayLessons.add(new Lesson("Англійська мова", new Time(12, 30), "205"));
        saturdayLessons.add(new Lesson("Англійська мова", new Time(13, 35), "205"));

        schedule = new ArrayList<>();
        schedule.add(mondayLessons);
        schedule.add(tuesdayLessons);
        schedule.add(wednesdayLessons);
        schedule.add(thursdayLessons);
        schedule.add(fridayLessons);
        schedule.add(saturdayLessons);
    }
}
