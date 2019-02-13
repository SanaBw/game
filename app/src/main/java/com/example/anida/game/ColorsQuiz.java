package com.example.anida.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class ColorsQuiz extends AppCompatActivity {

    private ImageView imageQuestion, imageQuestionMix1, imageQuestionMix2;
    private Button buttonAnswer1, buttonAnswer2, buttonAnswer3;
    private Button[] buttons;
    private TextView textAnd;
    private int correctAnswers, correctImage, i, x, randomInt, soundID, incorrectAnswers;
    private Random random;
    private Color[] primary;
    private Color[] mixed;
    private Color[] allColors;
    private String correctAnswer;
    private Color mix1, mix2;
    private AudioAttributes attributes;
    private SoundPool soundPool;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextView correct, incorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors_quiz);

        imageQuestion = findViewById(R.id.image_question);
        imageQuestionMix1 = findViewById(R.id.image_q_mix1);
        imageQuestionMix2 = findViewById(R.id.image_q_mix2);
        buttonAnswer1 = findViewById(R.id.button_answer1);
        buttonAnswer2 = findViewById(R.id.button_answer2);
        buttonAnswer3 = findViewById(R.id.button_answer3);
        buttons = new Button[]{buttonAnswer1, buttonAnswer2, buttonAnswer3};
        correct = findViewById(R.id.text_correct);
        incorrect = findViewById(R.id.text_incorrect);
        textAnd = findViewById(R.id.text_and);
        attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        soundPool = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(attributes).build();
        correctAnswers = 0;
        incorrectAnswers = 0;
        i = 0;
        x = 0;
        correctImage = 0;
        randomInt = -1;
        allColors = ColorsActivity.getColors();
        random = new Random();
        correctAnswer = "";

        sharedPreferences = getSharedPreferences("ColorsProgress", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        if (!(sharedPreferences.getString("today", "").equals(date))) {
            editor.clear();
            editor.apply();
        }

        //check how many primary and mixed colors
        for (Color color : allColors) {
            if (color.isPrimary()) {
                i++;
            } else {
                x++;
            }
        }

        //set sizes of array and fill them
        primary = new Color[i];
        mixed = new Color[x];
        i = 0;
        x = 0;
        for (Color color : allColors) {
            if (color.isPrimary()) {
                primary[i] = color;
                i++;
            } else {
                mixed[x] = color;
                x++;
            }
        }

        for (final Button b : buttons) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (correctAnswers < 2) { //first two questions with primary colors
                        if (b.getText().equals(correctAnswer)) {
                            correctAnswers++;
                            correct.setText(((Integer) correctAnswers).toString());
                            askAQuestion();
                        } else {
                            b.setBackgroundColor(android.graphics.Color.RED);
                            incorrectAnswers++;
                            incorrect.setText(((Integer) incorrectAnswers).toString());
                        }
                    } else { //second two questions with mixed colors
                        if (b.getTag().equals(correctAnswer)) {
                            correctAnswers++;
                            correct.setText(((Integer) correctAnswers).toString());
                            if (correctAnswers == 4) {
                                soundID = soundPool.load(getApplicationContext(), R.raw.claps, 1);
                                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                                    @Override
                                    public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                                        soundPool.play(soundID, 1, 1, 1, 0, 1f);
                                    }
                                });
                                Stats.saveProgress(sharedPreferences, correctAnswers, incorrectAnswers);
                                finish();
                            } else {
                                askAQuestion();
                            }
                        } else {
                            b.setVisibility(View.INVISIBLE);
                            incorrectAnswers++;
                            incorrect.setText(((Integer) incorrectAnswers).toString());
                        }
                    }
                }
            });
        }

        askAQuestion();
    }

    public void askAQuestion() {
        //restart buttons each time
        for (Button b : buttons) {
            b.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            b.setText("");
            b.setTag("");
            b.setVisibility(View.VISIBLE);
        }

        //first two questions with primary colors
        if (correctAnswers < 2) {
            imageQuestionMix1.setVisibility(View.GONE);
            imageQuestionMix2.setVisibility(View.GONE);
            textAnd.setVisibility(View.GONE);

            //choose random primary color
            randomInt = random.nextInt(primary.length);
            imageQuestion.setImageResource(primary[randomInt].getImage());
            correctAnswer = primary[randomInt].getColor();

            //choose random button to place correct answer
            randomInt = random.nextInt(3);
            if (buttons[randomInt].getText().equals("")) {
                buttons[randomInt].setText(correctAnswer);
            }

            //place random colors in the rest of the buttons which are empty
            for (int i = 0; i < 3; i++) {
                if (buttons[i].getText().equals("")) {
                    randomInt = random.nextInt(allColors.length);
                    do {
                        buttons[i].setText(allColors[randomInt].getColor());
                    } while (allColors[randomInt].getColor().equals(correctAnswer));
                }
            }
        } else { //second two questions with mixed colors
            imageQuestionMix1.setVisibility(View.VISIBLE);
            imageQuestionMix2.setVisibility(View.VISIBLE);
            textAnd.setVisibility(View.VISIBLE);
            imageQuestion.setVisibility(View.INVISIBLE);

            //choose random mixed color
            randomInt = random.nextInt(mixed.length);
            correctAnswer = mixed[randomInt].getColor();
            correctImage = mixed[randomInt].getImage();
            mix1 = mixed[randomInt].getMixColor1();
            mix2 = mixed[randomInt].getMixColor2();
            imageQuestionMix1.setImageResource(mix1.getImage());
            imageQuestionMix2.setImageResource(mix2.getImage());

            //choose random button to place correct image
            randomInt = random.nextInt(3);
            if (buttons[randomInt].getTag().equals("")) {
                buttons[randomInt].setTag(correctAnswer);
                buttons[randomInt].setBackgroundResource(correctImage);
            }

            //place random images in the rest of the buttons which are empty
            for (int i = 0; i < 3; i++) {
                if (buttons[i].getTag().equals("")) {
                    randomInt = random.nextInt(allColors.length);
                    do {
                        buttons[i].setBackgroundResource(allColors[randomInt].getImage());
                    } while (allColors[randomInt].getColor().equals(correctAnswer));
                }
            }
        }
    }
}
