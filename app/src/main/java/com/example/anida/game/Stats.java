package com.example.anida.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Stats extends AppCompatActivity {

    TextView lettersCorrect, lettersIncorrect, numbersCorrect, numbersIncorrect, daysCorrect, daysIncorrect, monthsCorrect, monthsIncorrect, animalsCorrect, animalsIncorrect, colorsCorrect, colorsIncorrect;
    SharedPreferences sharedPreferencesLetters, sharedPreferencesNumbers, sharedPreferencesDays, sharedPreferencesMonths, sharedPreferencesAnimals, sharedPreferencesColors;
    SharedPreferences.Editor editorLetters, editorDays, editorMonths, editorAnimals, editorNumbers, editorColors;
    Button buttonRestart;
    TextView[] texts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        lettersCorrect = findViewById(R.id.letters_correct);
        lettersIncorrect = findViewById(R.id.letters_incorrect);
        numbersCorrect = findViewById(R.id.numbers_correct);
        numbersIncorrect = findViewById(R.id.numbers_incorrect);
        daysCorrect = findViewById(R.id.days_correct);
        daysIncorrect = findViewById(R.id.days_incorrect);
        monthsCorrect = findViewById(R.id.months_correct);
        monthsIncorrect = findViewById(R.id.months_incorrect);
        animalsCorrect = findViewById(R.id.animals_correct);
        animalsIncorrect = findViewById(R.id.animals_incorrect);
        colorsCorrect = findViewById(R.id.colors_correct);
        colorsIncorrect = findViewById(R.id.colors_incorrect);
        texts = new TextView[]{lettersCorrect, lettersIncorrect, numbersCorrect, numbersIncorrect, daysCorrect, daysIncorrect, monthsCorrect, monthsIncorrect, animalsCorrect, animalsIncorrect, colorsCorrect, colorsIncorrect};
        buttonRestart = findViewById(R.id.button_restart);
        sharedPreferencesLetters = getSharedPreferences("LettersProgress", Context.MODE_PRIVATE);
        editorLetters = sharedPreferencesLetters.edit();
        sharedPreferencesNumbers = getSharedPreferences("NumbersProgress", Context.MODE_PRIVATE);
        editorNumbers = sharedPreferencesNumbers.edit();
        sharedPreferencesDays = getSharedPreferences("DaysProgress", Context.MODE_PRIVATE);
        editorDays = sharedPreferencesDays.edit();
        sharedPreferencesMonths = getSharedPreferences("MonthsProgress", Context.MODE_PRIVATE);
        editorMonths = sharedPreferencesMonths.edit();
        sharedPreferencesAnimals = getSharedPreferences("AnimalsProgress", Context.MODE_PRIVATE);
        editorAnimals = sharedPreferencesAnimals.edit();
        sharedPreferencesColors = getSharedPreferences("ColorsProgress", Context.MODE_PRIVATE);
        editorColors = sharedPreferencesColors.edit();

        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editorLetters.clear();
                editorLetters.apply();
                editorNumbers.clear();
                editorNumbers.apply();
                editorDays.clear();
                editorDays.apply();
                editorMonths.clear();
                editorMonths.apply();
                editorAnimals.clear();
                editorAnimals.apply();
                editorColors.clear();
                editorColors.apply();

                for (TextView tv : texts) {
                    tv.setText("0");
                }
            }
        });

        lettersCorrect.setText(((Integer) sharedPreferencesLetters.getInt("correct", 0)).toString());
        lettersIncorrect.setText(((Integer) sharedPreferencesLetters.getInt("incorrect", 0)).toString());
        numbersCorrect.setText(((Integer) sharedPreferencesNumbers.getInt("correct", 0)).toString());
        numbersIncorrect.setText(((Integer) sharedPreferencesNumbers.getInt("incorrect", 0)).toString());
        daysCorrect.setText(((Integer) sharedPreferencesDays.getInt("correct", 0)).toString());
        daysIncorrect.setText(((Integer) sharedPreferencesDays.getInt("incorrect", 0)).toString());
        monthsCorrect.setText(((Integer) sharedPreferencesMonths.getInt("correct", 0)).toString());
        monthsIncorrect.setText(((Integer) sharedPreferencesMonths.getInt("incorrect", 0)).toString());
        animalsCorrect.setText(((Integer) sharedPreferencesAnimals.getInt("correct", 0)).toString());
        animalsIncorrect.setText(((Integer) sharedPreferencesAnimals.getInt("incorrect", 0)).toString());
        colorsCorrect.setText(((Integer) sharedPreferencesColors.getInt("correct", 0)).toString());
        colorsIncorrect.setText(((Integer) sharedPreferencesColors.getInt("incorrect", 0)).toString());
    }


    public static void saveProgress(SharedPreferences sharedPreferences, int correct, int incorrect) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        int correctSaved = sharedPreferences.getInt("correct", 0) + correct;
        int incorrectSaved = sharedPreferences.getInt("incorrect", 0) + incorrect;

        editor.putInt("correct", correctSaved);
        editor.putInt("incorrect", incorrectSaved);
        editor.putString("today", date);
        editor.apply();
    }
}
