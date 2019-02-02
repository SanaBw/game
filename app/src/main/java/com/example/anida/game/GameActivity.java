package com.example.anida.game;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private ImageView imageGame;
    private Button answer1, answer2, answer3;
    private Button[] buttons;
    private Game rabbit, rainbow, flower, toyStory, catFood;
    private Game[] games;
    private Random random;
    private int randomInt, correctAnswers, i, done;
    private int[] doneMazes;
    private TextView textScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        imageGame = findViewById(R.id.image_game);
        answer1 = findViewById(R.id.button_answer1);
        answer2 = findViewById(R.id.button_answer2);
        answer3 = findViewById(R.id.button_answer3);
        buttons = new Button[]{answer1, answer2, answer3};
        textScore = findViewById(R.id.text_score);
        rabbit = new Game("rabbit", R.mipmap.quiz1, R.mipmap.carrot, new int[]{R.mipmap.cabbage, R.mipmap.tomato});
        rainbow = new Game("rainbow", R.mipmap.quiz2, R.mipmap.butterfly2, new int[]{R.mipmap.butterfly1, R.mipmap.butterfly3});
        flower = new Game("flower", R.mipmap.quiz3, R.mipmap.bee, new int[]{R.mipmap.wasp, R.mipmap.ladybug});
        toyStory = new Game("toyStory", R.mipmap.quiz4, R.mipmap.woody, new int[]{R.mipmap.mrpotato, R.mipmap.buzz});
        catFood = new Game("catFood", R.mipmap.quiz5, R.mipmap.cat1, new int[]{R.mipmap.cat2, R.mipmap.cat3});
        games = new Game[]{rabbit, rainbow, flower, toyStory, catFood};
        random = new Random();
        randomInt = 0;
        correctAnswers = 0;
        i = 0;
        done = 0;
        doneMazes = new int[]{-1, -1, -1, -1, -1};

        pickAMaze();

        for (final Button b : buttons) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (b.getTag().equals("correct")) {
                        System.out.println("TRUE");
                        correctAnswers++;
                    }
                    done++;
                    if (done == games.length) {
                        imageGame.setVisibility(View.GONE);
                        for (Button b : buttons) {
                            b.setVisibility(View.GONE);
                        }
                        textScore.setVisibility(View.VISIBLE);
                        textScore.setText("Your score: " + correctAnswers + " out of " + games.length);
                    } else {
                        pickAMaze();
                    }
                }
            });
        }
    }

    public void pickAMaze() {
        for (Button b : buttons) {
            b.setTag("");
        }

        boolean contains;

        if (i != 0) { // if it's not the first time to run activity, don't repeat mazes
            do {
                randomInt = random.nextInt(games.length);
                contains = false;
                for (int maze : doneMazes) {
                    if (randomInt == maze) {
                        contains = true;
                    }
                }
            }
            while (contains);
        } else { //if it's first time, just chose random number
            randomInt = random.nextInt(games.length);
        }

        int chosen = randomInt;
        doneMazes[i] = randomInt; //place it as done
        i++;
        imageGame.setBackgroundResource(games[chosen].getGameImage());

        //choose random button to place correct answer
        randomInt = random.nextInt(3);
        buttons[randomInt].setBackgroundResource(games[chosen].getCorrectAnswerImage());
        buttons[randomInt].setTag("correct");

        System.out.println("CORRECT IS " + games[chosen].getName());

        //place random image in the rest of the buttons which are empty
        int x = 0;
        for (Button b : buttons) {
            if (b.getTag().equals("")) {
                b.setBackgroundResource(games[chosen].getAnswerImages()[x]);
                b.setTag("incorrect");
                x++;
            }
        }
    }
}
