package com.example.lpmlschedule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lpmlschedule.R;
import com.example.lpmlschedule.model.Lesson;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LessonAdapter extends ArrayAdapter<Lesson> {

    public static final int LESSON_DURATION = 45;

    public LessonAdapter(@NonNull Context context, @NonNull List<Lesson> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_schedule,
                    parent, false);
        }

        Lesson lesson = getItem(position);

        TextView numberTextView = convertView.findViewById(R.id.item_schedule_number);
        numberTextView.setText(Integer.toString(position + 1));

        TextView subjectTextView = convertView.findViewById(R.id.item_schedule_subject);
        subjectTextView.setText(lesson.getSubject());

        TextView roomTextView = convertView.findViewById(R.id.item_schedule_room);
        roomTextView.setText(lesson.getRoom());

        TextView timeTextView = convertView.findViewById(R.id.item_schedule_time);
        timeTextView.setText(lesson.getStartTime() + "-"
                + lesson.getStartTime().addMinutes(LESSON_DURATION));

        return convertView;
    }
}
