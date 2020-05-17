package com.example.lpmlschedule.model;

public class Time {

    private int hour;
    private int minute;

    public Time() {
    }

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public Time addMinutes(int minutes) {
        int newMinutes = this.minute + minutes % 60;
        int newHours = this.hour + minutes / 60;
        if (newMinutes >= 60) {
            newMinutes -= 60;
            newHours += 1;
        }
        return new Time(newHours, newMinutes);
    }

    @Override
    public String toString() {
        return this.hour + ":" + (this.minute < 10 ? "0" : "") + this.minute;
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
