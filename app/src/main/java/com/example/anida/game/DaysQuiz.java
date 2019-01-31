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

    private TextView monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    private TextView[] textViews;
    private Day[] days;
    private Button buttonOK;
    private MediaPlayer mediaPlayer;
    private int a, correct;
    private Animation flip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days_quiz);

        days = DaysActivity.getDays();
        monday = findViewById(R.id.edit_monday);
        tuesday = findViewById(R.id.edit_tuesday);
        wednesday = findViewById(R.id.edit_wednesday);
        thursday = findViewById(R.id.edit_thursday);
        friday = findViewById(R.id.edit_friday);
        saturday = findViewById(R.id.edit_saturday);
        sunday = findViewById(R.id.edit_sunday);
        textViews = new TextView[]{monday, tuesday, wednesday, thursday, friday, saturday, sunday};
        buttonOK = findViewById(R.id.button_ok);
        flip = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flip);
        correct = 0;

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correct = 0;
                for (a = 0; a < textViews.length; a++) { //check every textview for correct day
                    if (textViews[a].getText().toString().toLowerCase().equals(days[a].getDay().toLowerCase())) {
                        correct++;
                        textViews[a].setTextColor(Color.GREEN);
                    } else if (!textViews[a].getText().toString().equals("")) { // if it's not empty nor correct
                        textViews[a].setTextColor(Color.RED);
                        textViews[a].startAnimation(flip);
                    } else { //if it's empty
                        textViews[a].startAnimation(flip);
                    }
                }

                if (correct == days.length) { //if they all correct
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
