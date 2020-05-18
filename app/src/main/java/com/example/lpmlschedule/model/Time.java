package com.example.lpmlschedule.model;

public class Time {

    public static final int MINUTES_IN_HOUR = 60;

    private int hour;
    private int minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public Time() {
    }

    @Override
    public String toString() {
        return this.hour + ":" + (this.minute < 10 ? "0" : "") + this.minute;
    }

    public Time addMinutes(int minutes) {
        int newMinutes = this.minute + minutes % MINUTES_IN_HOUR;
        int newHour = this.hour + minutes / MINUTES_IN_HOUR;
        if (newMinutes >= MINUTES_IN_HOUR) {
            newMinutes -= MINUTES_IN_HOUR;
            newHour++;
        }
        return new Time(newHour, newMinutes);
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
