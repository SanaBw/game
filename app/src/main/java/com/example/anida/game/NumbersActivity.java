package com.example.anida.game;

import android.content.Context;
import android.content.Intent;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class NumbersActivity extends AppCompatActivity {

    static ImageView imageBravo2;
    static Animation fadeIn;
    static MediaPlayer mediaPlayer;
    static Context context;
    private static String[] numbersDataset, numbersString;
    private static int[] numbersSounds, numbersImages;

    public static void bravo() {

        imageBravo2.startAnimation(fadeIn);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageBravo2.setVisibility(View.VISIBLE);
                mediaPlayer = MediaPlayer.create(context, R.raw.claps);
                mediaPlayer.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageBravo2.setVisibility(View.GONE); //hide after animation ends
                mediaPlayer.stop();
                mediaPlayer.release();

                //start the quiz
                Intent intent = new Intent(context, NumbersQuiz.class);
                intent.putExtra("numbersDataset", numbersDataset);
                intent.putExtra("numbersString", numbersString);
                intent.putExtra("numbersImages", numbersImages);
                intent.putExtra("numbersSounds", numbersSounds);
                context.startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        numbersDataset = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
        numbersString = new String[]{"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};
        numbersSounds = new int[]{R.raw.number0, R.raw.number1, R.raw.number2, R.raw.number3, R.raw.number4, R.raw.number5, R.raw.number6, R.raw.number7, R.raw.number8, R.raw.number9, R.raw.number10, R.raw.number11,
                R.raw.number12, R.raw.number13, R.raw.number14, R.raw.number15, R.raw.number16, R.raw.number17, R.raw.number18, R.raw.number19, R.raw.number20};
        numbersImages = new int[]{R.color.black_overlay, R.mipmap.one, R.mipmap.two, R.mipmap.three, R.mipmap.four, R.mipmap.five, R.mipmap.six, R.mipmap.seven, R.mipmap.eight, R.mipmap.nine, R.mipmap.ten, R.mipmap.eleven,
                R.mipmap.twelve, R.mipmap.thirteen, R.mipmap.fourteen, R.mipmap.fifteen, R.mipmap.sixteen, R.mipmap.seventeen, R.mipmap.eighteen, R.mipmap.nineteen, R.mipmap.twenty};
        imageBravo2 = findViewById(R.id.image_bravo_two);
        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        context = getApplicationContext();
        RecyclerView recyclerView = findViewById(R.id.recycler_view_numbers);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapterNumbers = new RecyclerAdapterNumbers(numbersDataset, numbersString, numbersSounds, numbersImages, R.layout.number, context);
        recyclerView.setAdapter(adapterNumbers);
    }
}
