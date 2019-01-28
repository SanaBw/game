package com.example.anida.game;

public class Animal {

    private String name;
    private int sound;
    private int picture;
    private int pronunciation;

    public Animal(String name, int sound, int pronunciation, int picture){
        this.name=name;
        this.sound=sound;
        this.pronunciation=pronunciation;
        this.picture=picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public int getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(int pronunciation) {
        this.pronunciation = pronunciation;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }
}
