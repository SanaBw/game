package com.example.anida.game;

public class Letter {

    private String letter;
    private int sound;
    private int image;

    public Letter(String letter, int sound, int image) {
        this.letter = letter;
        this.sound = sound;
        this.image = image;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
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
