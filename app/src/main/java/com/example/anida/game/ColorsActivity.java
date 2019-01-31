package com.example.anida.game;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ColorsActivity extends AppCompatActivity {

    private Color white, black, red, blue, yellow, orange, green, purple, pink, brown, gray;
    private static Color[] colors;
    private static Context context;
    private static ImageView imageBravo;
    private static MediaPlayer mediaPlayer;
    private static Animation fadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        white = new Color("white", R.mipmap.white, R.raw.white, true);
        black = new Color("black", R.mipmap.black, R.raw.black, true);
        red = new Color("red", R.mipmap.red, R.raw.red, true);
        blue = new Color("blue", R.mipmap.blue, R.raw.blue, true);
        yellow = new Color("yellow", R.mipmap.yellow, R.raw.yellow, true);
        orange = new Color("orange", R.mipmap.orange, R.raw.orange, yellow, red);
        green = new Color("green", R.mipmap.green, R.raw.green, yellow, blue);
        purple = new Color("purple", R.mipmap.purple, R.raw.purple, blue, red);
        pink = new Color("pink", R.mipmap.pink, R.raw.pink, white, red);
        brown = new Color("brown", R.mipmap.brown, R.raw.brown, orange, blue);
        gray = new Color("gray", R.mipmap.gray, R.raw.gray, black, white);
        colors = new Color[]{white, black, red, blue, yellow, orange, green, purple, pink, brown, gray};
        context = getApplicationContext();
        imageBravo = findViewById(R.id.image_bravo_rainbow);
        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_colors);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        RecyclerView.Adapter adapterColors = new RecyclerAdapterColors(colors, R.layout.color, context);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterColors);
    }

    public static void bravo() {
        imageBravo.setVisibility(View.VISIBLE);
        imageBravo.startAnimation(fadeIn);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mediaPlayer = MediaPlayer.create(context, R.raw.magic);
                mediaPlayer.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageBravo.setVisibility(View.GONE); //hide after animation ends
                mediaPlayer.stop();
                mediaPlayer.release();

                //start the quiz
                //Intent intent = new Intent(context, NumbersQuiz.class);
                //context.startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public static Color[] getColors() {
        return colors;
    }
}
