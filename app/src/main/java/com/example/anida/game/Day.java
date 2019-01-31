package com.example.anida.game;

public class Day {

    private String day;
    private int sound;

    public Day(String day,  int sound) {
        this.day = day;
        this.sound = sound;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }
}
