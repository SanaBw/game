package com.example.anida.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MonthsQuiz extends AppCompatActivity {

    private Month[] months;
    private ConstraintLayout constraintLayout;
    private TextView textSeason, textMonth1, textMonth2, textMonth3, textCorrect, textFalse;
    private ImageView imageCorrect, imageFalse;
    private int rand, numberOfQuestions;
    private Button buttonOk;
    private boolean winter, spring, summer, fall;
    private Random random;
    private int correct, correctTotal, incorrectTotal;
    private String season1, season2;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monts_quiz);

        months = MonthsActivity.getMonths();
        constraintLayout = findViewById(R.id.month_quiz_layout);
        textSeason = findViewById(R.id.text_season);
        textMonth1 = findViewById(R.id.text_month1);
        textMonth2 = findViewById(R.id.text_month2);
        textMonth3 = findViewById(R.id.text_month3);
        buttonOk = findViewById(R.id.button_ok);
        textCorrect = findViewById(R.id.text_correct);
        textFalse = findViewById(R.id.text_false);
        imageCorrect = findViewById(R.id.image_correct);
        imageFalse = findViewById(R.id.image_false);
        random = new Random();
        numberOfQuestions = 0;
        correctTotal = 0;
        incorrectTotal = 0;
        season1 = "";
        season2 = "";
        sharedPreferences = getSharedPreferences("MonthsProgress", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        if (!(sharedPreferences.getString("today", "").equals(date))) {
            editor.clear();
            editor.apply();
        }

        setSeason();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSeason(textMonth1.getText().toString().toLowerCase(), textMonth2.getText().toString().toLowerCase(), textMonth3.getText().toString().toLowerCase());
            }
        });

    }

    private void setSeason() {
        //clear buttons' texts
        textMonth1.setText("");
        textMonth2.setText("");
        textMonth3.setText("");
        //set counters invisible
        textCorrect.setVisibility(View.INVISIBLE);
        textFalse.setVisibility(View.INVISIBLE);
        imageCorrect.setVisibility(View.INVISIBLE);
        imageFalse.setVisibility(View.INVISIBLE);

        //chose random number until it's different from previous asked
        do {
            rand = random.nextInt(months.length);
        }
        while (months[rand].getSeason().equals(season1) || months[rand].getSeason().equals(season2));


        if (season1.equals("")) {//if this is the first question, season1 will be empty, so set this questions answer in season1
            season1 = months[rand].getSeason();
        } else {
            season2 = months[rand].getSeason();
        }

        //set appropriate background and question text
        int background = months[rand].getSeasonImage();
        String text = months[rand].getSeason();

        constraintLayout.setBackgroundResource(background);
        textSeason.setText(text);

        switch ((text)) {
            case "winter":
                winter = true;
                spring = false;
                summer = false;
                fall = false;
                break;
            case "spring":
                winter = false;
                spring = true;
                summer = false;
                fall = false;
                break;
            case "summer":
                winter = false;
                spring = false;
                summer = true;
                fall = false;
                break;
            case "autumn/fall":
                winter = false;
                spring = false;
                summer = false;
                fall = true;
                break;
        }

        numberOfQuestions++;
    }

    private void checkSeason(String month1, String month2, String month3) {
        String[] textMonths = new String[]{month1, month2, month3};
        correct = 0;
        String correct1 = "", correct2 = "";

        for (String text : textMonths) {
            if (winter) {
                if ((text.equals("january") || text.equals("february") || text.equals("december")) && (!text.equals(correct1) && (!text.equals(correct2)))) { //if it's winter month AND if it's not repeated answer
                    //place correct answer in empty string
                    if (correct1.equals("")) {
                        correct1 = text;
                    } else {
                        correct2 = text;
                    }
                    correct++;
                    correctTotal++;
                } else {
                    incorrectTotal++;
                    tryAgain();
                }
            } else if (spring) {
                if ((text.equals("march") || text.equals("april") || text.equals("may")) && (!text.equals(correct1) && (!text.equals(correct2)))) {
                    if (correct1.equals("")) {
                        correct1 = text;
                    } else {
                        correct2 = text;
                    }
                    correctTotal++;
                    correct++;
                } else {
                    incorrectTotal++;
                    tryAgain();
                }
            } else if (fall) {
                if ((text.equals("september") || text.equals("october") || text.equals("november")) && (!text.equals(correct1) && (!text.equals(correct2)))) {
                    if (correct1.equals("")) {
                        correct1 = text;
                    } else {
                        correct2 = text;
                    }
                    correctTotal++;
                    correct++;
                } else {
                    incorrectTotal++;
                    tryAgain();
                }
            } else if (summer) {
                if ((text.equals("june") || text.equals("july") || text.equals("august")) && (!text.equals(correct1) && (!text.equals(correct2)))) {
                    if (correct1.equals("")) {
                        correct1 = text;
                    } else {
                        correct2 = text;
                    }
                    correctTotal++;
                    correct++;
                } else {
                    incorrectTotal++;
                    tryAgain();
                }
            }
        }

        if (correct == 3) {
            if (numberOfQuestions == 3) {
                Stats.saveProgress(sharedPreferences, correctTotal, incorrectTotal);
                finish();
            } else {
                setSeason();
            }
        }
    }

    private void tryAgain() {
        //show counters
        textCorrect.setVisibility(View.VISIBLE);
        textFalse.setVisibility(View.VISIBLE);
        imageCorrect.setVisibility(View.VISIBLE);
        imageFalse.setVisibility(View.VISIBLE);

        textCorrect.setText(Integer.toString(correct));
        textFalse.setText(Integer.toString(3 - correct));
    }
}
