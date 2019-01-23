package com.example.anida.game;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DaysQuiz extends AppCompatActivity {

    TextView monday,tuesday,wednesday,thursday,friday,saturday,sunday;
    TextView[] textViews;
    String[] days;
    Button buttonOK;
    MediaPlayer mediaPlayer;
    int a, correct;
    Animation flip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days_quiz);

        monday=findViewById(R.id.edit_monday);
        tuesday=findViewById(R.id.edit_tuesday);
        wednesday=findViewById(R.id.edit_wednesday);
        thursday=findViewById(R.id.edit_thursday);
        friday=findViewById(R.id.edit_friday);
        saturday=findViewById(R.id.edit_saturday);
        sunday=findViewById(R.id.edit_sunday);
        textViews = new TextView[]{monday,tuesday,wednesday,thursday,friday,saturday,sunday};
        days = new String[]{"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
        buttonOK=findViewById(R.id.button_ok);
        flip = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flip);
        correct=0;

        buttonOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                correct=0;
                for(a=0;a<textViews.length;a++){
                    if (textViews[a].getText().toString().toLowerCase().equals(days[a])){
                        correct++;
                        textViews[a].setTextColor(Color.GREEN);
                        System.out.println(correct);
                    } else if (!textViews[a].getText().toString().equals("")){
                        textViews[a].setTextColor(Color.RED);
                        textViews[a].startAnimation(flip);
                    } else {
                        textViews[a].startAnimation(flip);
                    }
                }

                if (correct==7){
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.claps);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            finish();
                        }
                    });
                }
            }


        });

    }


}
