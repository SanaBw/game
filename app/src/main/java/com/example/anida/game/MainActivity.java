package com.example.anida.game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button buttonSound;
    private boolean soundPlaying;
    private MediaPlayer mediaPlayer;
    private int length;
    private Intent intent;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        length = 0;
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song);
        Animation rotateBall = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);

        Button buttonLetters = findViewById(R.id.button_letters);
        Button buttonNumbers = findViewById(R.id.button_numbers);
        Button buttonDays = findViewById(R.id.button_days);
        Button buttonMonths = findViewById(R.id.button_months);
        Button buttonAnimals = findViewById(R.id.button_animals);
        Button buttonColors = findViewById(R.id.button_colors);
        Button buttonQuizes = findViewById(R.id.button_quizes);
        Button[] buttons = new Button[]{buttonLetters, buttonNumbers, buttonDays, buttonMonths, buttonAnimals, buttonColors, buttonQuizes};
        buttonSound = findViewById(R.id.button_sound);
        ImageView imageGame = findViewById(R.id.image_game);

        mediaPlayer.start();
        soundPlaying = true;
        imageGame.startAnimation(rotateBall);

        for (Button b : buttons) { //onTouch because of animations --returns false aka. not pressed, no new activity
            b.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    darkenButton(v, event);
                    return false;
                }
            });
        }

        buttonLetters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, LettersActivity.class);
                startActivity(intent);
            }
        });

        buttonNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(intent);
            }
        });

        buttonDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 intent = new Intent(MainActivity.this, DaysActivity.class);
                 startActivity(intent);
            }
        });

        buttonMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, MonthsActivity.class);
                startActivity(intent);

            }
        });

        buttonAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, AnimalsActivity.class);
                 startActivity(intent);
            }
        });
        buttonColors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 intent = new Intent(MainActivity.this, ColorsActivity.class);
                 startActivity(intent);
            }
        });

        buttonQuizes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, QuizesActivity.class);
                startActivity(intent);
            }
        });

        imageGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        buttonSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundPlaying) {
                    pauseMusic();
                    soundPlaying = false;
                    buttonSound.setBackgroundResource(R.mipmap.nosound);
                } else {
                    resumeMusic();
                    buttonSound.setBackgroundResource(R.mipmap.sound);
                    soundPlaying = true;
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseMusic(); //when we pause activity(minimize) songs stops playing
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (soundPlaying) {
            resumeMusic(); //when we resume activity(start it again) songs starts playing if the sound isn't muted
        }
    }

    public void pauseMusic() {
        mediaPlayer.pause();
        length = mediaPlayer.getCurrentPosition();
    }

    public void resumeMusic() {
        mediaPlayer.seekTo(length);
        mediaPlayer.start();
    }

    public void darkenButton(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Button view = (Button) v;
                view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                v.invalidate();
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                Button view = (Button) v;
                view.getBackground().clearColorFilter();
                view.invalidate();
                break;
            }
        }
    }
}
