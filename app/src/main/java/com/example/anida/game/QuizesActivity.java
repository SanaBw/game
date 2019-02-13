package com.example.anida.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QuizesActivity extends AppCompatActivity {

    Button buttonLetter, buttonNumber, buttonDay, buttonMonth, buttonAnimal, buttonColor, buttonStats;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizes);

        buttonLetter = findViewById(R.id.button_letter_quiz);
        buttonNumber = findViewById(R.id.button_number_quiz);
        buttonDay = findViewById(R.id.button_day_quiz);
        buttonMonth = findViewById(R.id.button_month_quiz);
        buttonAnimal = findViewById(R.id.button_animal_quiz);
        buttonColor = findViewById(R.id.button_color_quiz);
        buttonStats = findViewById(R.id.button_stats);

        buttonStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(QuizesActivity.this, Stats.class);
                startActivity(intent);
            }
        });

        if (LettersActivity.getLetters() == null) {
            buttonLetter.setAlpha(0.3f);
            buttonLetter.setClickable(false);
        } else {
            buttonLetter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(QuizesActivity.this, LettersQuiz.class);
                    startActivity(intent);
                }
            });
        }

        if (NumbersActivity.getNumbers() == null) {
            buttonNumber.setAlpha(0.3f);
            buttonNumber.setClickable(false);
        } else {
            buttonNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(QuizesActivity.this, NumbersQuiz.class);
                    startActivity(intent);
                }
            });
        }

        if (DaysActivity.getDays() == null) {
            buttonDay.setAlpha(0.3f);
            buttonDay.setClickable(false);
        } else {
            buttonDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(QuizesActivity.this, DaysQuiz.class);
                    startActivity(intent);
                }
            });
        }

        if (MonthsActivity.getMonths() == null) {
            buttonMonth.setAlpha(0.3f);
            buttonMonth.setClickable(false);
        } else {
            buttonMonth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(QuizesActivity.this, MonthsQuiz.class);
                    startActivity(intent);
                }
            });
        }

        if (AnimalsActivity.getAnimals() == null) {
            buttonAnimal.setAlpha(0.3f);
            buttonAnimal.setClickable(false);
        } else {
            buttonAnimal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(QuizesActivity.this, AnimalsQuiz.class);
                    startActivity(intent);
                }
            });
        }

        if (ColorsActivity.getColors() == null) {
            buttonColor.setAlpha(0.3f);
            buttonColor.setClickable(false);
        } else {
            buttonColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(QuizesActivity.this, ColorsQuiz.class);
                    startActivity(intent);
                }
            });
        }
    }
}
