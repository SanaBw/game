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
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class LettersQuiz extends AppCompatActivity {

    private Letter[] letters;
    private Button buttonSound, buttonA, buttonB, buttonC, buttonD, buttonE, buttonF, buttonG, buttonH, buttonI, buttonJ, buttonK, buttonL, buttonM, buttonN, buttonO, buttonP, buttonQ, buttonR, buttonS, buttonT, buttonU, buttonV,
            buttonW, buttonX, buttonY, buttonZ, buttonExit;
    private Button[] buttons;
    private SoundPool soundPool;
    private int soundID, soundID2, soundID3, soundID4, randomInt, randomLetterSound, randomIntTwo, randomLetterSoundTwo, randomIntThree, randomLetterSoundThree, randomIntFour, randomLetterSoundFour;
    private AudioAttributes attributes;
    private Random random;
    private String letterPlaying, letterPlayingTwo, letterPlayingThree, letterPlayingFour;
    private Animation animation;
    private int correctAnswers, incorrectAnswersTotal, correctAnswersTotal;
    private int buttonColor;
    private TextView correct, incorrect;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

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
        correctAnswersTotal = 0;
        incorrectAnswersTotal = 0;
        buttonColor = getResources().getColor(R.color.colorPrimary);
        correct = findViewById(R.id.text_correct);
        incorrect = findViewById(R.id.text_incorrect);
        sharedPreferences = getSharedPreferences("LettersProgress", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        if (!(sharedPreferences.getString("today", "").equals(date))) {
            editor.clear();
            editor.apply();
        }

        buttonSound.startAnimation(animation);
        chooseRandomLetter();

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

        //onclicklistener for each letter
        for (Button b : buttons) {
            final Button button = b;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if correct
                    if (button.getText().toString().toLowerCase().equals(letterPlaying)) {
                        correctAnswersTotal++;
                        correct.setText(((Integer) correctAnswersTotal).toString());

                        buttonSound.startAnimation(animation);
                        for (Button b : buttons) {
                            b.setBackgroundColor(buttonColor);
                        }
                        chooseRandomLetterTwo();
                    }
                    //if not correct make button red
                    else {
                        button.setBackgroundColor(Color.RED);
                        incorrectAnswersTotal++;
                        incorrect.setText(((Integer) incorrectAnswersTotal).toString());
                    }
                }
            });
        }
    }

    public void chooseRandomLetterTwo() {
        randomInt = random.nextInt(letters.length);
        randomIntTwo = random.nextInt(letters.length);
        randomLetterSound = letters[randomInt].getSound();
        randomLetterSoundTwo = letters[randomIntTwo].getSound();

        soundID = soundPool.load(getApplicationContext(), randomLetterSound, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                soundPool.play(soundID, 1, 1, 1, 0, 1f);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            soundID2 = soundPool.load(getApplicationContext(), randomLetterSoundTwo, 2);
                            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                                @Override
                                public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                                    soundPool.play(soundID2, 1, 1, 1, 0, 1f);
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        letterPlaying = letters[randomInt].getLetter();
        letterPlayingTwo = letters[randomIntTwo].getLetter();

        //play letter sound on button click
        buttonSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundID, 1, 1, 1, 0, 1f);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            soundPool.play(soundID2, 1, 1, 1, 0, 1f);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        for (Button b : buttons) {
            final Button button = b;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if correct
                    if (button.getText().toString().toLowerCase().equals(letterPlaying) || button.getText().toString().toLowerCase().equals(letterPlayingTwo)) {
                        correctAnswersTotal++;
                        correct.setText(((Integer) correctAnswersTotal).toString());
                        if (correctAnswers == 1) {
                            buttonSound.startAnimation(animation);
                            for (Button b : buttons) {
                                b.setBackgroundColor(buttonColor);
                            }
                            chooseRandomLetterThree();
                        } else {
                            correctAnswers++;
                            button.setBackgroundColor(Color.GREEN);
                        }
                    }
                    //if not correct make button red
                    else {
                        incorrectAnswersTotal++;
                        incorrect.setText(((Integer) incorrectAnswersTotal).toString());
                        button.setBackgroundColor(Color.RED);
                    }
                }
            });
        }
    }


    public void chooseRandomLetterThree() {
        correctAnswers = 0;
        randomInt = random.nextInt(letters.length);
        randomIntTwo = random.nextInt(letters.length);
        randomIntThree = random.nextInt(letters.length);
        randomLetterSound = letters[randomInt].getSound();
        randomLetterSoundTwo = letters[randomIntTwo].getSound();
        randomLetterSoundThree = letters[randomIntThree].getSound();

        soundID = soundPool.load(getApplicationContext(), randomLetterSound, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                soundPool.play(soundID, 1, 1, 1, 0, 1f);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            soundID2 = soundPool.load(getApplicationContext(), randomLetterSoundTwo, 2);
                            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                                @Override
                                public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                                    soundPool.play(soundID2, 1, 1, 1, 0, 1f);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(1000);
                                                soundID3 = soundPool.load(getApplicationContext(), randomLetterSoundThree, 3);
                                                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                                                    @Override
                                                    public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                                                        soundPool.play(soundID3, 1, 1, 1, 0, 1f);
                                                    }
                                                });
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        letterPlaying = letters[randomInt].getLetter();
        letterPlayingTwo = letters[randomIntTwo].getLetter();
        letterPlayingThree = letters[randomIntThree].getLetter();

        //play letter sound on button click
        buttonSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundID, 1, 1, 1, 0, 1f);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            soundPool.play(soundID2, 1, 1, 1, 0, 1f);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                        soundPool.play(soundID3, 1, 1, 1, 0, 1f);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        for (Button b : buttons) {
            final Button button = b;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if correct
                    if (button.getText().toString().toLowerCase().equals(letterPlaying) || button.getText().toString().toLowerCase().equals(letterPlayingTwo) || button.getText().toString().toLowerCase().equals(letterPlayingThree)) {
                        correctAnswersTotal++;
                        correct.setText(((Integer) correctAnswersTotal).toString());

                        if (correctAnswers == 2) {
                            buttonSound.startAnimation(animation);
                            for (Button b : buttons) {
                                b.setBackgroundColor(buttonColor);
                            }
                            chooseRandomLetterFour();
                        } else {
                            correctAnswers++;
                            button.setBackgroundColor(Color.GREEN);
                        }
                    }
                    //if not correct make button red
                    else {
                        incorrectAnswersTotal++;
                        incorrect.setText(((Integer) incorrectAnswersTotal).toString());
                        button.setBackgroundColor(Color.RED);
                    }
                }
            });
        }
    }

    public void chooseRandomLetterFour() {
        correctAnswers = 0;
        randomInt = random.nextInt(letters.length);
        randomIntTwo = random.nextInt(letters.length);
        randomIntThree = random.nextInt(letters.length);
        randomIntFour = random.nextInt(letters.length);
        randomLetterSound = letters[randomInt].getSound();
        randomLetterSoundTwo = letters[randomIntTwo].getSound();
        randomLetterSoundThree = letters[randomIntThree].getSound();
        randomLetterSoundFour = letters[randomIntFour].getSound();

        soundID = soundPool.load(getApplicationContext(), randomLetterSound, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                soundPool.play(soundID, 1, 1, 1, 0, 1f);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            soundID2 = soundPool.load(getApplicationContext(), randomLetterSoundTwo, 2);
                            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                                @Override
                                public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                                    soundPool.play(soundID2, 1, 1, 1, 0, 1f);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(1000);
                                                soundID3 = soundPool.load(getApplicationContext(), randomLetterSoundThree, 3);
                                                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                                                    @Override
                                                    public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                                                        soundPool.play(soundID3, 1, 1, 1, 0, 1f);
                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                try {
                                                                    Thread.sleep(1000);
                                                                    soundID4 = soundPool.load(getApplicationContext(), randomLetterSoundFour, 4);
                                                                    soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                                                                        @Override
                                                                        public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                                                                            soundPool.play(soundID4, 1, 1, 1, 0, 1f);
                                                                        }
                                                                    });
                                                                } catch (InterruptedException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        }).start();
                                                    }
                                                });
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        letterPlaying = letters[randomInt].getLetter();
        letterPlayingTwo = letters[randomIntTwo].getLetter();
        letterPlayingThree = letters[randomIntThree].getLetter();
        letterPlayingFour = letters[randomIntFour].getLetter();

        //play letter sound on button click
        buttonSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundID, 1, 1, 1, 0, 1f);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            soundPool.play(soundID2, 1, 1, 1, 0, 1f);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                        soundPool.play(soundID3, 1, 1, 1, 0, 1f);
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    Thread.sleep(1000);
                                                    soundPool.play(soundID4, 1, 1, 1, 0, 1f);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        for (Button b : buttons) {
            final Button button = b;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if correct
                    if (button.getText().toString().toLowerCase().equals(letterPlaying) || button.getText().toString().toLowerCase().equals(letterPlayingTwo) || button.getText().toString().toLowerCase().equals(letterPlayingThree)
                            || button.getText().toString().toLowerCase().equals(letterPlayingFour)) {
                        correctAnswersTotal++;
                        correct.setText(((Integer) correctAnswersTotal).toString());

                        if (correctAnswers == 3) {
                            soundID = soundPool.load(getApplicationContext(), R.raw.claps, 1);
                            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                                @Override
                                public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                                    soundPool.play(soundID, 1, 1, 1, 0, 1f);
                                }
                            });
                            Stats.saveProgress(sharedPreferences, correctAnswersTotal, incorrectAnswersTotal);
                            finish();
                        } else {
                            correctAnswers++;
                            button.setBackgroundColor(Color.GREEN);
                        }
                    }
                    //if not correct make button red
                    else {
                        incorrectAnswersTotal++;
                        incorrect.setText(((Integer) incorrectAnswersTotal).toString());
                        button.setBackgroundColor(Color.RED);
                    }
                }
            });
        }
    }
}


