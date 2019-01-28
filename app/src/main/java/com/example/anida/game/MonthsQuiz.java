package com.example.anida.game;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MonthsQuiz extends AppCompatActivity {

    private String[] monthsDatasetString, monthsSeasons;
    private int[] monthsDataset, seasonImages, randoms;
    private ConstraintLayout constraintLayout;
    private TextView textSeason, textMonth1, textMonth2, textMonth3,textCorrect, textFalse;
    ImageView imageCorrect, imageFalse;
    int rand, numberOfQuestions;
    Button buttonOk;
    boolean winter, spring, summer, fall;
    Random random;
    int correct;
    String season1, season2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monts_quiz);

        monthsDataset = getIntent().getIntArrayExtra("monthsDataset");
        monthsDatasetString = getIntent().getStringArrayExtra("monthsDatasetString");
        monthsSeasons = getIntent().getStringArrayExtra("monthsSeasons");
        seasonImages = new int[]{R.mipmap.backmonthwin, R.mipmap.backmonthwin, R.mipmap.backmonthspr, R.mipmap.backmonthspr, R.mipmap.backmonthspr, R.mipmap.backmonthsum, R.mipmap.backmonthsum, R.mipmap.backmonthsum,
                R.mipmap.backmonthfall, R.mipmap.backmonthfall, R.mipmap.backmonthfall, R.mipmap.backmonthwin};
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
        season1 = "";
        season2= "";
        setSeason();


        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSeason(textMonth1.getText().toString().toLowerCase(), textMonth2.getText().toString().toLowerCase(), textMonth3.getText().toString().toLowerCase());
            }
        });

    }

    private void setSeason() {

        textMonth1.setText("");
        textMonth2.setText("");
        textMonth3.setText("");
        textCorrect.setVisibility(View.INVISIBLE);
        textFalse.setVisibility(View.INVISIBLE);
        imageCorrect.setVisibility(View.INVISIBLE);
        imageFalse.setVisibility(View.INVISIBLE);


        do {
            rand = random.nextInt(monthsSeasons.length);
        }
        while (monthsSeasons[rand].equals(season1) || monthsSeasons[rand].equals(season2) );

        if(season1.equals("")){
            season1=monthsSeasons[rand];
        } else {
            season2=monthsSeasons[rand];
        }

        int background = seasonImages[rand];
        String text = monthsSeasons[rand];
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
        String correct1="", correct2="";

        for (String text : textMonths) {
            if (winter) {
                if ((text.equals("january") || text.equals("february") || text.equals("december")) && (!text.equals(correct1) && (!text.equals(correct2)))) {
                    if (correct1.equals("")){
                        correct1 = text;
                    } else {
                        correct2=text;
                    }
                    correct++;
                } else {
                    tryAgain();
                }
            } else if (spring) {
                if ((text.equals("march") || text.equals("april") || text.equals("may")) && (!text.equals(correct1) && (!text.equals(correct2)))) {
                    if (correct1.equals("")){
                        correct1 = text;
                    } else {
                        correct2=text;
                    }
                    correct++;
                } else {
                    tryAgain();
                }
            } else if (fall) {
                if ((text.equals("september") || text.equals("october") || text.equals("november")) && (!text.equals(correct1) && (!text.equals(correct2)))) {
                    if (correct1.equals("")){
                        correct1 = text;
                    } else {
                        correct2=text;
                    }
                    correct++;
                } else {
                    tryAgain();
                }
            } else if (summer) {
                if ((text.equals("june") || text.equals("july") || text.equals("august")) && (!text.equals(correct1) && (!text.equals(correct2)))) {
                    if (correct1.equals("")){
                        correct1 = text;
                    } else {
                        correct2=text;
                    }
                    correct++;
                } else {
                    tryAgain();
                }
            }


        }

        if (correct == 3) {
            if (numberOfQuestions == 4 ) {
                finish();
            } else {
                setSeason();
            }
        }
    }

    private void tryAgain() {

        textCorrect.setVisibility(View.VISIBLE);
        textFalse.setVisibility(View.VISIBLE);
        imageCorrect.setVisibility(View.VISIBLE);
        imageFalse.setVisibility(View.VISIBLE);

        textCorrect.setText(Integer.toString(correct));
        textFalse.setText(Integer.toString(3-correct));
    }

}
