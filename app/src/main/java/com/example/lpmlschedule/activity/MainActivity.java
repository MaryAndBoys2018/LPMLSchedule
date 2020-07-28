package com.example.lpmlschedule.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lpmlschedule.R;
import com.example.lpmlschedule.adapter.LessonAdapter;
import com.example.lpmlschedule.model.Lesson;
import com.example.lpmlschedule.model.Time;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
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
    LessonAdapter adapter;
    private final List<String> daysList =
            new ArrayList(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"));

    private int currentTab;

    private ListView listView;
    private TabLayout tabLayout;

    //TODO change constant to corresponding field of user from firestore

    private final String CURRENT_CLASS = "11-A";

    private FloatingActionButton addLessonFab;
    private Dialog dialog;
    private EditText numberEditText;
    private EditText nameEditText;
    private EditText roomEditText;
    private Button cancelButton;
    private Button confirmButton;

    protected BottomNavigationView navigation;
    private FirebaseFirestore firebaseFirestore;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
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
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.schedule);

        initializeSchedule();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

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
        /** TODO add reading data from shared preferences and in case it is present open dialog containing all data
        * https://developer.android.com/reference/android/content/SharedPreferences
        */
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

                // додаємо даний урок на фаєрбейз
                firebaseFirestore.collection(CURRENT_CLASS)
                        .document(daysList.get(currentTab))
                        .collection("lessons").add(lessonToAdd).addOnSuccessListener( aVoid -> {
                    currentSchedule.add(lessonNumber - 1,  lessonToAdd);
                    adapter.notifyDataSetChanged();
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Виникла помилка", Toast.LENGTH_LONG).show();
                });
                dialog.dismiss();
        });
    }

    public void initializeSchedule() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        currentSchedule = new ArrayList<>();
        // отримуємо розклад кожного дня з фаєрстору, якщо це понеділок, то відображаємо ці уроки
        daysList.forEach( day -> firebaseFirestore.collection(CURRENT_CLASS).document(day)
                .collection("lessons")
                .orderBy("startTime").get()
                .addOnSuccessListener( queryDocumentSnapshots -> {
                    schedule.put(day, queryDocumentSnapshots.toObjects(Lesson.class));
                    if (day.equals(daysList.get(0))) {
                        currentSchedule.addAll(schedule.get(day));
                        adapter.notifyDataSetChanged();
                    }
                })
        );
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


    /** TODO add getting info from editTexts and writing it to shared preferences
     * https://developer.android.com/reference/android/content/SharedPreferences
     */
    @Override
    protected void onPause() {
        super.onPause();
    }
}
