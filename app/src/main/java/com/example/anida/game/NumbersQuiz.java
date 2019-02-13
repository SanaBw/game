package com.example.anida.game;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class NumbersQuiz extends AppCompatActivity {

    private Number[] numbers;
    private ImageView imageNumber;
    private Button button1, button2, button3, buttonExit;
    private Button[] buttons;
    private SoundPool soundPool;
    private int soundID, randomInt, randomInt2, randomInt3, randomNumberImage;
    private AudioAttributes attributes;
    private Random random, randomButton, randomRest;
    private String correctAnswer;
    private Animation animation;
    private int correctAnswers, incorrectAnswers;
    private int buttonColor;
    private TextView correct, incorrect;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_quiz);

        numbers = NumbersActivity.getNumbers();
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
        incorrectAnswers = 0;
        correctAnswer = "";
        buttonColor = getResources().getColor(R.color.colorPrimaryDark);
        correct = findViewById(R.id.text_correct);
        incorrect = findViewById(R.id.text_incorrect);
        sharedPreferences = getSharedPreferences("NumbersProgress", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        if (!(sharedPreferences.getString("today", "").equals(date))) {
            editor.clear();
            editor.apply();
        }

        chooseRandomNumber();

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //set onclicklistener for each answer button
        for (final Button b : buttons) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if answer is correct, either next question or congrats
                    if (b.getText().equals(correctAnswer)) {
                        correctAnswers++;
                        correct.setText(((Integer) correctAnswers).toString());
                        if (correctAnswers == 4) { //congrats after 4th correct answer
                            soundID = soundPool.load(getApplicationContext(), R.raw.claps, 1);
                            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                                @Override
                                public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                                    soundPool.play(soundID, 1, 1, 1, 0, 1f);
                                }
                            });
                            Stats.saveProgress(sharedPreferences, correctAnswers, incorrectAnswers);
                            finish();
                        } else { //new question
                            chooseRandomNumber();
                        }
                    } else { //if answer is not correct
                        incorrectAnswers++;
                        incorrect.setText(((Integer) incorrectAnswers).toString());
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
        randomInt = random.nextInt(numbers.length);
        randomNumberImage = numbers[randomInt].getImage();
        imageNumber.setImageResource(randomNumberImage);
        imageNumber.startAnimation(animation);

        //first two questions with numbers, next two with words of number - set correct
        if (correctAnswers < 2) {
            correctAnswer = (String.valueOf(numbers[randomInt].getNumber()));
        } else {
            correctAnswer = numbers[randomInt].getString();
        }

        //choose random button to place correct answer
        randomInt2 = randomButton.nextInt(3);
        if (buttons[randomInt2].getText().equals("")) {
            buttons[randomInt2].setText(correctAnswer);
        }

        //place random numbers in the rest of the buttons which are empty
        for (int i = 0; i < 3; i++) {
            if (buttons[i].getText().equals("")) {
                randomInt3 = randomRest.nextInt(numbers.length);
                //if random is same as correct answer, change it (add or subtract 1)
                if (randomInt3 == randomInt) {
                    if (randomInt != 20) { //if it's not number 20, add 1
                        randomInt3 = randomInt + 1;
                    } else { //if it's 20, subtract 1
                        randomInt3 = randomInt - 1;
                    }
                }

                //first two questions with numbers, next two with words of number - set text to buttons
                if (correctAnswers < 2) {
                    buttons[i].setText(Integer.toString(randomInt3)); //had some issues with .setText, so converted int to string
                } else {
                    buttons[i].setText(numbers[randomInt3].getString());
                }
            }
        }
    }
}
