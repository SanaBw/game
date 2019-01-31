package com.example.anida.game;

public class Color {

    private String color;
    private int image;
    private int sound;
    private boolean isPrimary;
    private Color mixColor1;
    private Color mixColor2;

    public Color(String color, int image, int sound, boolean isPrimary) {
        this.color = color;
        this.image = image;
        this.sound = sound;
        this.isPrimary = isPrimary;
        this.mixColor1 = null;
        this.mixColor2 = null;
    }

    public Color(String color, int image, int sound, Color mix1, Color mix2) {
        this.color = color;
        this.image = image;
        this.sound = sound;
        this.isPrimary = false;
        this.mixColor1 = mix1;
        this.mixColor2 = mix2;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Color getMixColor1() {
        return mixColor1;
    }

    public void setMixColor1(Color mixColor1) {
        this.mixColor1 = mixColor1;
    }

    public Color getMixColor2() {
        return mixColor2;
    }

    public void setMixColor2(Color mixColor2) {
        this.mixColor2 = mixColor2;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }
}
