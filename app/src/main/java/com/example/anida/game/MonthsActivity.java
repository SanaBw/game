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

    static ImageView imageBravo;
    static Animation fadeIn;
    static MediaPlayer mediaPlayer;
    static Context context;
    private static String[] monthsDatasetString, monthsSeasons;
    private static int[] monthsDataset, monthsSounds, monthsImages;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_months);

        context = getApplicationContext();
        monthsDataset = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        monthsDatasetString = new String[]{"january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december"};
        monthsSeasons = new String[]{"winter", "winter", "spring", "spring", "spring", "summer", "summer", "summer", "autumn/fall", "autumn/fall", "autumn/fall", "winter"};
        monthsSounds = new int[]{R.raw.january, R.raw.february, R.raw.march, R.raw.april, R.raw.may, R.raw.june, R.raw.july, R.raw.august, R.raw.september, R.raw.october, R.raw.november, R.raw.december};
        monthsImages = new int[]{R.mipmap.january, R.mipmap.february, R.mipmap.march, R.mipmap.april, R.mipmap.may, R.mipmap.june, R.mipmap.july, R.mipmap.august, R.mipmap.september, R.mipmap.october,
                R.mipmap.november, R.mipmap.december};
        imageBravo=findViewById(R.id.image_bravo_months);
        fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        recyclerView = findViewById(R.id.recycler_view_months);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        final RecyclerView.Adapter adapterNumbers = new RecyclerAdapterMonths(monthsDataset, monthsDatasetString, monthsSeasons, monthsSounds, monthsImages, R.layout.month, context);
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
                intent.putExtra("monthsDataset",monthsDataset);
                intent.putExtra("monthsDatasetString",monthsDatasetString);
                intent.putExtra("monthsSeasons",monthsSeasons);
                context.startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
    }
}
