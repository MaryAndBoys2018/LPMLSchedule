package com.example.lpmlschedule.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.lpmlschedule.R;
import com.example.lpmlschedule.adapter.LessonAdapter;
import com.example.lpmlschedule.model.Lesson;
import com.example.lpmlschedule.model.Time;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Map<String, List<Lesson>> schedule = new HashMap<>();
    private List<Lesson> currentSchedule;
    private List<Lesson> mondayLessons;
    private List<Lesson> tuesdayLessons;
    private List<Lesson> wednesdayLessons;
    private List<Lesson> thursdayLessons;
    private List<Lesson> fridayLessons;
    private List<Lesson> saturdayLessons;
    LessonAdapter adapter;
    private final List<String> daysList =
            new ArrayList(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"));

    private int currentTab;

    private ListView listView;
    private TabLayout tabLayout;
    private final String CURRENT_CLASS = "11-Г";

    private FloatingActionButton addLessonFab;
    private Dialog dialog;
    private EditText numberEditText;
    private EditText nameEditText;
    private EditText roomEditText;
    private Button cancelButton;
    private Button confirmButton;

    protected BottomNavigationView navigation;
    private FirebaseFirestore firebaseFirestore;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.schedule:
                    return true;
                case R.id.deals:
                    Intent dealsActivityIntent = new Intent(MainActivity.this, DealActivity.class);
                    MainActivity.this.startActivity(dealsActivityIntent);
                    return true;
                case R.id.homework:
                    Intent homeworkActivityIntent = new Intent(MainActivity.this, HomeworkActivity.class);
                    MainActivity.this.startActivity(homeworkActivityIntent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.schedule);

        initializeSchedule();

        listView = findViewById(R.id.schedule_list);
        adapter = new LessonAdapter(this, currentSchedule);
        listView.setAdapter(adapter);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab.getPosition();
                currentSchedule.clear();
                currentSchedule.addAll(schedule.get(daysList.get(currentTab)));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        addLessonFab = findViewById(R.id.add_lesson_fab);

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_schedule);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        numberEditText = dialog.findViewById(R.id.number_edit_text);
        nameEditText = dialog.findViewById(R.id.name_edit_text);
        roomEditText = dialog.findViewById(R.id.room_edit_text);
        cancelButton = dialog.findViewById(R.id.cancel);
        confirmButton = dialog.findViewById(R.id.confirm);

        addLessonFab.setOnClickListener(v -> dialog.show());

        cancelButton.setOnClickListener(v -> {
            dialog.dismiss();
            numberEditText.getText().clear();
            nameEditText.getText().clear();
            roomEditText.getText().clear();
        });

        confirmButton.setOnClickListener( v -> {
                int lessonNumber = Integer.parseInt(numberEditText.getText().toString());
                String lessonName = nameEditText.getText().toString();
                String lessonRoom = roomEditText.getText().toString();
                Lesson lessonToAdd = new Lesson(lessonName, createTime(lessonNumber - 1), lessonRoom);
                currentSchedule.add(lessonNumber - 1,  lessonToAdd);
                adapter.notifyDataSetChanged();
                dialog.dismiss();

        });
    }

    public void initializeSchedule() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        currentSchedule = new ArrayList<>();

        daysList.forEach( day -> firebaseFirestore.collection("classes")
                .document(CURRENT_CLASS)
                .collection(day).orderBy("startTime").get()
                .addOnSuccessListener( queryDocumentSnapshots -> {
                    schedule.put(day, queryDocumentSnapshots.toObjects(Lesson.class));
                    if (day.equals(daysList.get(0))) {
                        currentSchedule.addAll(schedule.get(day));
                        adapter.notifyDataSetChanged();
                    }
                })
        );
        mondayLessons = new ArrayList<>();
        mondayLessons.add(new Lesson("Алгебра", createTime(0), "None"));
        mondayLessons.add(new Lesson("Фізкультура", createTime(1), "None"));
        mondayLessons.add(new Lesson("Біологія", createTime(2), "None"));
        mondayLessons.add(new Lesson("Фізика", createTime(3), "None"));
        mondayLessons.add(new Lesson("Фізика", createTime(4), "None"));
        mondayLessons.add(new Lesson("Фізика", createTime(5), "None"));
        mondayLessons.add(new Lesson("Фізика", createTime(6), "None"));
        //mondayLessons.forEach(lesson -> firebaseFirestore.collection("classes")
                //.document(CURRENT_CLASS)
              //  .collection("Monday").add(lesson));

        tuesdayLessons = new ArrayList<>();
        tuesdayLessons.add(new Lesson("Уроку немає", createTime(0), "None"));
        tuesdayLessons.add(new Lesson("Уроку немає", createTime(1), "None"));
        tuesdayLessons.add(new Lesson("Уроку немає", createTime(2), "None"));
        tuesdayLessons.add(new Lesson("Уроку немає", createTime(3), "None"));
        tuesdayLessons.add(new Lesson("Уроку немає", createTime(4), "None"));
        tuesdayLessons.add(new Lesson("Уроку немає", createTime(5), "None"));
        tuesdayLessons.add(new Lesson("Уроку немає", createTime(6), "None"));
        tuesdayLessons.add(new Lesson("Уроку немає", createTime(7), "None"));

        wednesdayLessons = new ArrayList<>();
        wednesdayLessons.add(new Lesson("Уроку немає", createTime(0), "None"));
        wednesdayLessons.add(new Lesson("Уроку немає", createTime(1), "None"));
        wednesdayLessons.add(new Lesson("Уроку немає", createTime(2), "None"));
        wednesdayLessons.add(new Lesson("Уроку немає", createTime(3), "None"));
        wednesdayLessons.add(new Lesson("Уроку немає", createTime(4), "None"));
        wednesdayLessons.add(new Lesson("Уроку немає", createTime(5), "None"));
        wednesdayLessons.add(new Lesson("Уроку немає", createTime(6), "None"));
        wednesdayLessons.add(new Lesson("Уроку немає", createTime(7), "None"));

        thursdayLessons = new ArrayList<>();
        thursdayLessons.add(new Lesson("Уроку немає", createTime(0), "None"));
        thursdayLessons.add(new Lesson("Уроку немає", createTime(1), "None"));
        thursdayLessons.add(new Lesson("Уроку немає", createTime(2), "None"));
        thursdayLessons.add(new Lesson("Уроку немає", createTime(3), "None"));
        thursdayLessons.add(new Lesson("Уроку немає", createTime(4), "None"));
        thursdayLessons.add(new Lesson("Уроку немає", createTime(5), "None"));
        thursdayLessons.add(new Lesson("Уроку немає", createTime(6), "None"));
        thursdayLessons.add(new Lesson("Уроку немає", createTime(7), "None"));

        fridayLessons = new ArrayList<>();
        fridayLessons.add(new Lesson("Уроку немає", createTime(0), "None"));
        fridayLessons.add(new Lesson("Уроку немає", createTime(1), "None"));
        fridayLessons.add(new Lesson("Уроку немає", createTime(2), "None"));
        fridayLessons.add(new Lesson("Уроку немає", createTime(3), "None"));
        fridayLessons.add(new Lesson("Уроку немає", createTime(4), "None"));
        fridayLessons.add(new Lesson("Уроку немає", createTime(5), "None"));
        fridayLessons.add(new Lesson("Уроку немає", createTime(6), "None"));
        fridayLessons.add(new Lesson("Уроку немає", createTime(7), "None"));

        saturdayLessons = new ArrayList<>();
        saturdayLessons.add(new Lesson("Уроку немає", createTime(0), "None"));
        saturdayLessons.add(new Lesson("Уроку немає", createTime(1), "None"));
        saturdayLessons.add(new Lesson("Уроку немає", createTime(2), "None"));
        saturdayLessons.add(new Lesson("Уроку немає", createTime(3), "None"));
        saturdayLessons.add(new Lesson("Уроку немає", createTime(4), "None"));
        saturdayLessons.add(new Lesson("Уроку немає", createTime(5), "None"));
        saturdayLessons.add(new Lesson("Уроку немає", createTime(6), "None"));
        saturdayLessons.add(new Lesson("Уроку немає", createTime(7), "None"));
    }

    private Time createTime(int timeIndex) {
        int hours = 0;
        int minutes = 0;
        switch (timeIndex) {
            case 0:
                hours = 9;
                minutes = 0;
                break;
            case 1:
                hours = 9;
                minutes = 55;
                break;
            case 2:
                hours = 10;
                minutes = 50;
                break;
            case 3:
                hours = 11;
                minutes = 20;
                break;
            case 4:
                hours = 12;
                minutes = 25;
                break;
            case 5:
                hours = 13;
                minutes = 10;
                break;
            case 6:
                hours = 14;
                minutes = 20;
                break;
            case 7:
                hours = 15;
                minutes = 5;
                break;
        }
        return new Time(hours, minutes);
    }

    private List<Lesson> getDaySchedule(int scheduleDayIndex) {
        List<Lesson> currentList = new ArrayList<>();
        switch (scheduleDayIndex) {
            case 0:
                currentList = mondayLessons;
                break;
            case 1:
                currentList = tuesdayLessons;
                break;
            case 2:
                currentList = wednesdayLessons;
                break;
            case 3:
                currentList = thursdayLessons;
                break;
            case 4:
                currentList = fridayLessons;
                break;
            case 5:
                currentList = saturdayLessons;
                break;
        }
        return currentList;
    }
}
