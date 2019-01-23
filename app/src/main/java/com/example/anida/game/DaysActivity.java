package com.example.anida.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class DaysActivity extends AppCompatActivity {

    TextView[] textViews;
    TextView monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    Animation flip, bounceUp;
    int soundID;
    SoundPool soundPool;
    AudioAttributes attributes;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String[] days;
    private int[] daysSounds;
    ImageView imageBravo;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);

        days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        daysSounds = new int[]{R.raw.monday, R.raw.tuesday, R.raw.wednesday, R.raw.thursday, R.raw.friday, R.raw.saturday, R.raw.sunday};
        monday = findViewById(R.id.text_monday);
        tuesday = findViewById(R.id.text_tuesday);
        wednesday = findViewById(R.id.text_wednesday);
        thursday = findViewById(R.id.text_thursday);
        friday = findViewById(R.id.text_friday);
        saturday = findViewById(R.id.text_saturday);
        sunday = findViewById(R.id.text_sunday);
        textViews = new TextView[]{monday, tuesday, wednesday, thursday, friday, saturday, sunday};
        flip = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flip);
        bounceUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce_up_slower);
        attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        soundPool = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(attributes).build();
        sharedPreferences = getSharedPreferences("DaysDone", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        imageBravo=findViewById(R.id.image_bravo);

        //each button set text of day and onclicklistener
        for (int i = 0; i <= 6; i++) {
            final int index = i;
            textViews[i].setText(days[i]);
            textViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playAnimationAndSound(index);
                }
            });
        }
    }

    public void putInSP(int i) {
        editor.putBoolean(days[i], true);
        editor.apply();
        if (sharedPreferences.getAll().size() == 7) {
            editor.clear();
            editor.apply();
            bravo();
        }
    }

    public void playAnimationAndSound(int i) {
        final int index = i;
        textViews[i].startAnimation(flip);
        soundID = soundPool.load(getApplicationContext(), daysSounds[index], 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                soundPool.play(soundID, 1, 1, 1, 0, 1f);
                putInSP(index);
            }
        });
    }

    public void bravo() {
        imageBravo.setAlpha(1f); //because if it's second time to play the animation, alpha is 0f (onAnimationEnd)
        imageBravo.startAnimation(bounceUp);
        bounceUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageBravo.setVisibility(View.VISIBLE);
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.claps);
                mediaPlayer.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageBravo.setAlpha(0f); //hide after animation ends
                if (mediaPlayer != null) {
                    try {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //start the quiz
                Intent intent = new Intent(getApplicationContext(), DaysQuiz.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }
}

