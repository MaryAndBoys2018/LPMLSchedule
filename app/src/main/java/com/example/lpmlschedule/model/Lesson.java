package com.example.lpmlschedule.model;

public class Lesson {

    private String subject;
    private Time startTime;
    private String room;

    public Lesson(String subject, Time startTime, String room) {
        this.subject = subject;
        this.startTime = startTime;
        this.room = room;
    }

    public Lesson() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
