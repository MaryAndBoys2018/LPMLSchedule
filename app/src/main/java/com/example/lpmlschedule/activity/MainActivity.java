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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<List<Lesson>> schedule;
    private List<Lesson> currentSchedule;

    private ListView listView;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeSchedule();

        currentSchedule = new ArrayList<>();
        currentSchedule.addAll(schedule.get(0));

        listView = findViewById(R.id.schedule_list);
        final LessonAdapter adapter = new LessonAdapter(this, currentSchedule);
        listView.setAdapter(adapter);

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

    public void initializeSchedule() {
        List<Lesson> mondayLessons = new ArrayList<>();
        mondayLessons.add(new Lesson("Андроїд курси", new Time(9, 0), "205"));
        mondayLessons.add(new Lesson("Андроїд курси", new Time(10, 5), "205"));
        mondayLessons.add(new Lesson("Андроїд курси", new Time(11, 10), "205"));
        mondayLessons.add(new Lesson("Андроїд курси", new Time(12, 5), "205"));
        mondayLessons.add(new Lesson("Андроїд курси", new Time(13, 20), "205"));
        mondayLessons.add(new Lesson("Андроїд курси", new Time(14, 45), "205"));

        List<Lesson> tuesdayLessons = new ArrayList<>();
        tuesdayLessons.add(new Lesson("Інформатика", new Time(9, 0), "301"));
        tuesdayLessons.add(new Lesson("Інформатика", new Time(10, 5), "301"));
        tuesdayLessons.add(new Lesson("Інформатика", new Time(11, 10), "301"));
        tuesdayLessons.add(new Lesson("Інформатика", new Time(12, 5), "301"));
        tuesdayLessons.add(new Lesson("Інформатика", new Time(13, 20), "301"));
        tuesdayLessons.add(new Lesson("Інформатика", new Time(14, 45), "301"));

        List<Lesson> wednesdayLessons = new ArrayList<>();
        wednesdayLessons.add(new Lesson("Англійська мова", new Time(9, 0), "301"));
        wednesdayLessons.add(new Lesson("Англійська мова", new Time(10, 5), "301"));
        wednesdayLessons.add(new Lesson("Англійська мова", new Time(11, 10), "301"));
        wednesdayLessons.add(new Lesson("Англійська мова", new Time(12, 5), "301"));
        wednesdayLessons.add(new Lesson("Англійська мова", new Time(13, 20), "301"));
        wednesdayLessons.add(new Lesson("Англійська мова", new Time(14, 45), "301"));

        List<Lesson> thursdayLessons = new ArrayList<>();
        thursdayLessons.add(new Lesson("Математика", new Time(9, 0), "301"));
        thursdayLessons.add(new Lesson("Математика", new Time(10, 5), "301"));
        thursdayLessons.add(new Lesson("Математика", new Time(11, 10), "301"));
        thursdayLessons.add(new Lesson("Математика", new Time(12, 5), "301"));
        thursdayLessons.add(new Lesson("Математика", new Time(13, 20), "301"));
        thursdayLessons.add(new Lesson("Математика", new Time(14, 45), "301"));

        List<Lesson> fridayLessons = new ArrayList<>();
        fridayLessons.add(new Lesson("Фізика", new Time(9, 0), "301"));
        fridayLessons.add(new Lesson("Фізика", new Time(10, 5), "301"));
        fridayLessons.add(new Lesson("Фізика", new Time(11, 10), "301"));
        fridayLessons.add(new Lesson("Фізика", new Time(12, 5), "301"));
        fridayLessons.add(new Lesson("Фізика", new Time(13, 20), "301"));
        fridayLessons.add(new Lesson("Фізика", new Time(14, 45), "301"));

        List<Lesson> saturdayLessons = new ArrayList<>();
        saturdayLessons.add(new Lesson("Фізичне виховання", new Time(9, 0), "301"));
        saturdayLessons.add(new Lesson("Фізичне виховання", new Time(10, 5), "301"));
        saturdayLessons.add(new Lesson("Фізичне виховання", new Time(11, 10), "301"));
        saturdayLessons.add(new Lesson("Фізичне виховання", new Time(12, 5), "301"));
        saturdayLessons.add(new Lesson("Фізичне виховання", new Time(13, 20), "301"));
        saturdayLessons.add(new Lesson("Фізичне виховання", new Time(14, 45), "301"));

        schedule = new ArrayList<>();
        schedule.add(mondayLessons);
        schedule.add(tuesdayLessons);
        schedule.add(wednesdayLessons);
        schedule.add(thursdayLessons);
        schedule.add(fridayLessons);
        schedule.add(saturdayLessons);
    }
}
