package com.example.anida.game;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

public class MonthsActivity extends AppCompatActivity {

    private Month jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec;
    private static Month[] months;
    private static ImageView imageBravo;
    private static Animation fadeIn;
    private static MediaPlayer mediaPlayer;
    private static Context context;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_months);

        jan = new Month("january", 1, "winter", R.mipmap.backmonthwin, R.mipmap.january, R.raw.january);
        feb = new Month("february", 2, "winter", R.mipmap.backmonthwin, R.mipmap.february, R.raw.february);
        mar = new Month("march", 3, "spring", R.mipmap.backmonthspr, R.mipmap.march, R.raw.march);
        apr = new Month("april", 4, "spring", R.mipmap.backmonthspr, R.mipmap.april, R.raw.april);
        may = new Month("may", 5, "spring", R.mipmap.backmonthspr, R.mipmap.may, R.raw.may);
        jun = new Month("june", 6, "summer", R.mipmap.backmonthsum, R.mipmap.june, R.raw.june);
        jul = new Month("july", 7, "summer", R.mipmap.backmonthsum, R.mipmap.july, R.raw.july);
        aug = new Month("august", 8, "summer", R.mipmap.backmonthsum, R.mipmap.august, R.raw.august);
        sep = new Month("september", 9, "autumn/fall", R.mipmap.backmonthfall, R.mipmap.september, R.raw.september);
        oct = new Month("october", 10, "autumn/fall", R.mipmap.backmonthfall, R.mipmap.october, R.raw.october);
        nov = new Month("november", 11, "autumn/fall", R.mipmap.backmonthfall, R.mipmap.november, R.raw.november);
        dec = new Month("december", 12, "winter", R.mipmap.backmonthwin, R.mipmap.december, R.raw.december);
        months = new Month[]{jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec};
        context = getApplicationContext();
        imageBravo = findViewById(R.id.image_bravo_months);
        fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        recyclerView = findViewById(R.id.recycler_view_months);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        RecyclerView.Adapter adapterNumbers = new RecyclerAdapterMonths(months, R.layout.month, context);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterNumbers);
    }

    public static void bravo() {
        imageBravo.startAnimation(fadeIn);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageBravo.setVisibility(View.VISIBLE);
                mediaPlayer = MediaPlayer.create(context, R.raw.claps);
                mediaPlayer.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageBravo.setVisibility(View.GONE); //hide after animation ends
                mediaPlayer.stop();
                mediaPlayer.release();

                //start the quiz
                Intent intent = new Intent(context, MonthsQuiz.class);
                context.startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public static Month[] getMonths() {
        return months;
    }
}
