package com.example.anida.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AnimalsQuiz extends AppCompatActivity {

    private Animal[] animals;
    private ImageView imageAnimal;
    private Button buttonAnswer1, buttonAnswer2, buttonAnswer3, buttonExit;
    private Button[] buttons;
    private SoundPool soundPool;
    private int soundID, randomNumber, randomInt1, randomInt2;
    private AudioAttributes attributes;
    private Random random;
    private String correctAnswerString;
    private Animation animation;
    private int correctAnswers, incorrectAnswers, correctAnswerImage;
    private int buttonColor;
    private TextView correct, incorrect;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals_quiz);

        animals = AnimalsActivity.getAnimals();
        imageAnimal = findViewById(R.id.image_question);
        buttonAnswer1 = findViewById(R.id.button_answer1);
        buttonAnswer2 = findViewById(R.id.button_answer2);
        buttonAnswer3 = findViewById(R.id.button_answer3);
        buttons = new Button[]{buttonAnswer1, buttonAnswer2, buttonAnswer3};
        buttonExit = findViewById(R.id.button_exit);
        attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        soundPool = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(attributes).build();
        random = new Random();
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce_right);
        correctAnswerImage = -1;
        correctAnswers = 0;
        incorrectAnswers = 0;
        correctAnswerString = "";
        buttonColor = getResources().getColor(R.color.colorPrimaryDark);
        randomNumber = -10;
        correct = findViewById(R.id.text_correct);
        incorrect = findViewById(R.id.text_incorrect);
        sharedPreferences = getSharedPreferences("AnimalsProgress", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        if (!(sharedPreferences.getString("today", "").equals(date))) {
            editor.clear();
            editor.apply();
        }

        chooseRandomAnimal();

        //set onclicklistener for each answer button
        for (final Button b : buttons) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (correctAnswers <= 1) { //first two questions are with sound as question and pictures of animals as answers
                        //if answer is correct, either next question or congrats
                        if (b.getTag().equals("correct")) {
                            correctAnswers++;
                            correct.setText(((Integer) correctAnswers).toString());
                            chooseRandomAnimal();
                        } else { //if answer is not correct
                            incorrectAnswers++;
                            incorrect.setText(((Integer) incorrectAnswers).toString());
                            b.setVisibility(View.INVISIBLE);
                        }
                    } else { // second two questions are with picture as question and name of animal as answers
                        //if answer is correct, either next question or congrats
                        if (b.getText().equals(correctAnswerString)) {
                            correctAnswers++;
                            correct.setText(((Integer) correctAnswers).toString());
                            if (correctAnswers == 4) { //congrats after 4th correct answer
                                soundID = soundPool.load(getApplicationContext(), R.raw.claps, 1);
                                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                                    @Override
                                    public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                                        soundPool.play(soundID, 1, 1, 1, 0, 1f);

                                        //release sound after each sound (around 3000ms)
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    Thread.sleep(3000);
                                                    soundPool.release();
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                    }
                                });
                                Stats.saveProgress(sharedPreferences, correctAnswers, incorrectAnswers);
                                finish();
                            } else { //new question
                                chooseRandomAnimal();
                            }
                        } else { //if answer is not correct
                            incorrectAnswers++;
                            incorrect.setText(((Integer) incorrectAnswers).toString());
                            b.setBackgroundColor(Color.RED);
                        }
                    }
                }
            });
        }

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void chooseRandomAnimal() {
        //restart each time
        for (Button b : buttons) {
            b.setText("");
            b.setBackgroundColor(buttonColor);
            b.setTag("");
            b.setVisibility(View.VISIBLE);
        }

        if (correctAnswers <= 1) { // first two questions with sound and image of animal
            buttonAnswer1.getLayoutParams().height = Math.round(getResources().getDisplayMetrics().density * 150);
            buttonAnswer1.getLayoutParams().width = Math.round(getResources().getDisplayMetrics().density * 100);
            buttonAnswer2.getLayoutParams().height = Math.round(getResources().getDisplayMetrics().density * 150);
            buttonAnswer2.getLayoutParams().width = Math.round(getResources().getDisplayMetrics().density * 100);
            buttonAnswer3.getLayoutParams().height = Math.round(getResources().getDisplayMetrics().density * 150);
            buttonAnswer3.getLayoutParams().width = Math.round(getResources().getDisplayMetrics().density * 100);

            do {
                randomNumber = random.nextInt(animals.length);
            }
            while (!animals[randomNumber].hasSound()); //making sure not to pick no-sound animal

            correctAnswerImage = animals[randomNumber].getImage();
            imageAnimal.setImageResource(R.mipmap.voice);

            imageAnimal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    soundID = soundPool.load(getApplicationContext(), animals[randomNumber].getSound(), 1);
                    soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                        @Override
                        public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                            soundPool.play(soundID, 1, 1, 1, 0, 1f);
                        }
                    });
                }
            });

            //choose random button to place correct answer
            randomInt1 = random.nextInt(3);
            buttons[randomInt1].setBackgroundResource(correctAnswerImage);
            buttons[randomInt1].setTag("correct");

            //place random numbers in the rest of the buttons which are empty
            for (int i = 0; i < 3; i++) {
                if (buttons[i].getTag().equals("")) {
                    randomInt2 = random.nextInt(animals.length);
                    buttons[i].setBackgroundResource(animals[randomInt2].getImage());
                    buttons[i].setTag(animals[randomInt2].getImage());
                }
            }
        } else { //second two questions are with picture and name of animal
            for (Button b : buttons) {
                b.setBackgroundResource(0);
                b.setBackgroundColor(buttonColor);
                b.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                b.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            }

            randomNumber = random.nextInt(animals.length);
            correctAnswerString = animals[randomNumber].getName();
            imageAnimal.getLayoutParams().width = Math.round(getResources().getDisplayMetrics().density * 100);
            imageAnimal.getLayoutParams().height = Math.round(getResources().getDisplayMetrics().density * 150);
            imageAnimal.setImageResource(animals[randomNumber].getImage());
            imageAnimal.setOnClickListener(null);

            //choose random button to place correct answer
            randomInt1 = random.nextInt(3);
            buttons[randomInt1].setText(correctAnswerString);

            //place random numbers in the rest of the buttons which are empty
            for (int i = 0; i < 3; i++) {
                if (buttons[i].getText().equals("")) {
                    randomInt2 = random.nextInt(animals.length);
                    buttons[i].setText(animals[randomInt2].getName());
                }
            }
        }
    }


}
