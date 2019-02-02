package com.example.anida.game;

public class Game {

    private String name;
    private int gameImage;
    private int[] answerImages;
    private int correctAnswerImage;

    public Game(String name, int gameImage, int correctAnswerImage, int[] answerImages) {
        this.name = name;
        this.gameImage = gameImage;
        this.answerImages = answerImages;
        this.correctAnswerImage = correctAnswerImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGameImage() {
        return gameImage;
    }

    public void setGameImage(int gameImage) {
        this.gameImage = gameImage;
    }

    public int[] getAnswerImages() {
        return answerImages;
    }

    public void setAnswerImages(int[] answerImages) {
        this.answerImages = answerImages;
    }

    public int getCorrectAnswerImage() {
        return correctAnswerImage;
    }

    public void setCorrectAnswerImage(int correctAnswerImage) {
        this.correctAnswerImage = correctAnswerImage;
    }
}
