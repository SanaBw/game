package com.example.anida.game;

public class Month {

    private String month;
    private String season;
    private int seasonImage;
    private int number;
    private int image, sound;

    public Month(String month, int number, String season, int seasonImage, int image, int sound) {
        this.month = month;
        this.number = number;
        this.season = season;
        this.seasonImage = seasonImage;
        this.image = image;
        this.sound = sound;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getSeasonImage() {
        return seasonImage;
    }

    public void setSeasonImage(int seasonImage) {
        this.seasonImage = seasonImage;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }
}
