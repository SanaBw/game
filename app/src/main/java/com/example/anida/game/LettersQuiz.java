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

import java.util.Random;

public class LettersQuiz extends AppCompatActivity {

    private Letter[] letters;
    private Button buttonSound, buttonA, buttonB, buttonC, buttonD, buttonE, buttonF, buttonG, buttonH, buttonI, buttonJ, buttonK, buttonL, buttonM, buttonN, buttonO, buttonP, buttonQ, buttonR, buttonS, buttonT, buttonU, buttonV,
            buttonW, buttonX, buttonY, buttonZ, buttonExit;
    private Button[] buttons;
    private SoundPool soundPool;
    private int soundID, randomInt, randomLetterSound;
    private AudioAttributes attributes;
    private Random random;
    private String letterPlaying;
    private Animation animation;
    private int correctAnswers;
    private int buttonColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letters_quiz);

        letters = LettersActivity.getLetters();
        buttonSound = findViewById(R.id.button_question);
        buttonExit = findViewById(R.id.button_exit);
        buttons = new Button[]{
                buttonA = findViewById(R.id.button_a),
                buttonB = findViewById(R.id.button_b),
                buttonC = findViewById(R.id.button_c),
                buttonD = findViewById(R.id.button_d),
                buttonE = findViewById(R.id.button_e),
                buttonF = findViewById(R.id.button_f),
                buttonG = findViewById(R.id.button_g),
                buttonH = findViewById(R.id.button_h),
                buttonI = findViewById(R.id.button_i),
                buttonJ = findViewById(R.id.button_j),
                buttonK = findViewById(R.id.button_k),
                buttonL = findViewById(R.id.button_l),
                buttonM = findViewById(R.id.button_m),
                buttonN = findViewById(R.id.button_n),
                buttonO = findViewById(R.id.button_o),
                buttonP = findViewById(R.id.button_p),
                buttonQ = findViewById(R.id.button_q),
                buttonR = findViewById(R.id.button_r),
                buttonS = findViewById(R.id.button_s),
                buttonT = findViewById(R.id.button_t),
                buttonU = findViewById(R.id.button_u),
                buttonV = findViewById(R.id.button_v),
                buttonW = findViewById(R.id.button_w),
                buttonX = findViewById(R.id.button_x),
                buttonY = findViewById(R.id.button_y),
                buttonZ = findViewById(R.id.button_z)};
        attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        soundPool = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(attributes).build();
        random = new Random();
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce_up);
        correctAnswers = 0;
        buttonColor = getResources().getColor(R.color.colorPrimary);

        buttonSound.startAnimation(animation);
        chooseRandomLetter();

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //play letter sound on button click
        buttonSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundID = soundPool.load(getApplicationContext(), randomLetterSound, 1);
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                        soundPool.play(soundID, 1, 1, 1, 0, 1f);
                    }
                });
                letterPlaying = letters[randomInt].getLetter();
            }
        });

        //onclicklistener for each letter button to play sound and animation
        for (Button b : buttons) {
            final Button button = b;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if correct
                    if (button.getText().toString().toLowerCase().equals(letterPlaying)) {
                        //if this is third correct answer play clap and finish the quiz
                        if (correctAnswers == 2) {
                            soundID = soundPool.load(getApplicationContext(), R.raw.claps, 1);
                            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                                @Override
                                public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                                    soundPool.play(soundID, 1, 1, 1, 0, 1f);
                                }
                            });
                            finish();
                        } else {
                            //if it isn't third correct answer, set new question and change all button background to normal
                            buttonSound.startAnimation(animation);
                            chooseRandomLetter();
                            correctAnswers++;
                            for (Button b : buttons) {
                                b.setBackgroundColor(buttonColor);
                            }
                        }
                    }
                    //if not correct make button red
                    else {
                        button.setBackgroundColor(Color.RED);

                    }
                }
            });
        }
    }

    public void chooseRandomLetter() {
        random.nextInt();
        randomInt = random.nextInt(letters.length);
        randomLetterSound = letters[randomInt].getSound();
        soundID = soundPool.load(getApplicationContext(), randomLetterSound, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                soundPool.play(soundID, 1, 1, 1, 0, 1f);
            }
        });
        letterPlaying = letters[randomInt].getLetter();
    }
}


