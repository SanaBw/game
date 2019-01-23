package com.example.anida.game;

import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class NumbersQuiz extends AppCompatActivity {
    String[] numbersDataset;
    String[] numbersString;
    int[] numbersImages;
    int[] numbersSounds;
    ImageView imageNumber;
    Button button1, button2, button3, buttonExit;
    Button[] buttons;
    SoundPool soundPool;
    int soundID, randomInt, randomInt2, randomInt3, randomNumberImage;
    AudioAttributes attributes;
    Random random, randomButton, randomRest;
    String correctAnswer;
    Animation animation;
    int correctAnswers;
    int buttonColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_quiz);
        numbersDataset = getIntent().getStringArrayExtra("numbersDataset");
        numbersString = getIntent().getStringArrayExtra("numbersString");
        numbersImages = getIntent().getIntArrayExtra("numbersImages");
        numbersSounds = getIntent().getIntArrayExtra("numbersSounds");
        imageNumber = findViewById(R.id.image_question);
        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        buttons = new Button[]{button1, button2, button3};
        buttonExit = findViewById(R.id.button_exit);
        attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        soundPool = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(attributes).build();
        random = new Random();
        randomButton = new Random();
        randomRest = new Random();
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce_right);
        correctAnswers = 0;
        correctAnswer = "";
        buttonColor = getResources().getColor(R.color.colorPrimaryDark);

        chooseRandomNumber();

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        for (final Button b : buttons) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if answer is correct, either next question or congrats
                    if (b.getText().equals(correctAnswer)) {
                        correctAnswers++;
                        if (correctAnswers == 4) { //congrats
                            soundID = soundPool.load(getApplicationContext(), R.raw.claps, 1);
                            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                                @Override
                                public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                                    soundPool.play(soundID, 1, 1, 1, 0, 1f);
                                }
                            });
                            finish();
                        } else { //new question
                            chooseRandomNumber();
                        }
                    } else { //if answer is not correct
                        b.setBackgroundColor(Color.RED);
                    }
                }
            });
        }
    }

    private void chooseRandomNumber() {
        //restart buttons each time
        for (Button b : buttons) {
            b.setText("");
            b.setBackgroundColor(buttonColor);
        }

        //choose random number and its image
        randomInt = random.nextInt(numbersImages.length);
        randomNumberImage = numbersImages[randomInt];
        imageNumber.setImageResource(randomNumberImage);
        imageNumber.startAnimation(animation);

        //first two questions with numbers, next two with words of number
        if (correctAnswers < 2) {
            correctAnswer = numbersDataset[randomInt];
        } else {
            correctAnswer = numbersString[randomInt];
        }

        //choose random button to place correct answer
        randomInt2 = randomButton.nextInt(3);
        if (buttons[randomInt2].getText().equals("")) {
            buttons[randomInt2].setText(correctAnswer);
        }

        //place random numbers in the rest of the buttons
        for (int i = 0; i < 3; i++) {
            if (buttons[i].getText().equals("")) {
                randomInt3 = randomRest.nextInt(numbersImages.length);
                //if random is same as correct answer, we don't wanna duplicate it
                if (randomInt3 == randomInt) {
                    if (randomInt != 20) { //if it's not number 20, add 1
                        randomInt3 = randomInt + 1;
                    } else { //if it's 20, subtract 1
                        randomInt3 = randomInt - 1;
                    }
                }

                //first two questions with numbers, next two with words of number
                if (correctAnswers < 2) {
                    buttons[i].setText(Integer.toString(randomInt3)); //had some issues with .setText, so converted int to string
                } else {
                    buttons[i].setText(numbersString[randomInt3]);
                }
            }
        }

    }
}
