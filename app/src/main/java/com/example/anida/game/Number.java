package com.example.anida.game;

public class Number {

    private String string;
    private int number;
    private int sound;
    private int image;

    public Number(String string, int number, int sound, int image) {
        this.string = string;
        this.number = number;
        this.sound = sound;
        this.image = image;
    }

    public String getString() {
        return string;
    }

    public void setString(String numberString) {
        this.string = numberString;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
