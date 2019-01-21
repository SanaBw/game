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

        length=0;
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song);
        Animation rotateBall = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);

        Button buttonLetters = findViewById(R.id.button_letters);
        Button buttonNumbers = findViewById(R.id.button_numbers);
        Button buttonDays = findViewById(R.id.button_days);
        Button buttonMonths = findViewById(R.id.button_months);
        Button buttonAnimals = findViewById(R.id.button_animals);
        Button buttonColors = findViewById(R.id.button_colors);
        Button buttonQuizes = findViewById(R.id.button_quizes);
        buttonSound = findViewById(R.id.button_sound);
        ImageView imageGame = findViewById(R.id.image_game);

        mediaPlayer.start();
        soundPlaying=true;
        imageGame.startAnimation(rotateBall);


        //onTouch instead of onClick because of animations
        buttonLetters.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                darkenButton(v, event);
                intent = new Intent(MainActivity.this, LettersActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_SINGLE_TOP); //need flags because onTouchListener sometimes doubles the activity start
                startActivity(intent);
                return true;
            }
        });

        buttonNumbers.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                darkenButton(v, event);
                intent = new Intent(MainActivity.this, NumbersActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            }
        });

        buttonDays.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                darkenButton(v, event);
                return true;
            }
        });

        buttonMonths.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                darkenButton(v, event);
                return true;
            }
        });
        buttonAnimals.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                darkenButton(v, event);
                return true;
            }
        });
        buttonColors.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                darkenButton(v, event);
                return true;
            }
        });
        buttonQuizes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                darkenButton(v, event);
                return true;
            }
        });

        buttonSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundPlaying){
                    pauseMusic();
                    soundPlaying=false;
                    buttonSound.setBackgroundResource(R.mipmap.nosound);
                } else {
                    resumeMusic();
                    buttonSound.setBackgroundResource(R.mipmap.sound);
                    soundPlaying=true;
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
        if (soundPlaying){
            resumeMusic(); //when we resume activity(start it again) songs starts playing if the sound isn't muted
        }
    }

    public void pauseMusic(){
        mediaPlayer.pause();
        length=mediaPlayer.getCurrentPosition();
    }

    public void resumeMusic(){
        mediaPlayer.seekTo(length);
        mediaPlayer.start();
    }

    public void darkenButton(View v, MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Button view = (Button) v;
                view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                v.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP:
                // Your action here on button click
            case MotionEvent.ACTION_CANCEL: {
                Button view = (Button) v;
                view.getBackground().clearColorFilter();
                view.invalidate();
                break;
            }
        }
    }


}
