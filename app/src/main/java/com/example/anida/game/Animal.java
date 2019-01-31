package com.example.anida.game;

public class Animal {

    private String name;
    private int sound;
    private int image;
    private int pronunciation;
    private boolean hasSound;

    public Animal(String name, int sound,int pronunciation, int image){
        this.name=name;
        this.sound=sound;
        this.pronunciation=pronunciation;
        this.image=image;
        this.hasSound=true;
    }

    public Animal(String name, int sound, boolean hasSound, int pronunciation, int image){
        this.name=name;
        this.sound=sound;
        this.pronunciation=pronunciation;
        this.image=image;
        this.hasSound=hasSound;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean hasSound() {
        return hasSound;
    }

    public void hasSound(boolean hasSound) {
        this.hasSound = hasSound;
    }
}
