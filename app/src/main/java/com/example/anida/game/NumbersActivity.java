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

    private Number zero, one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen, fourteen, fifteen, sixteen, seventeen, eighteen, nineteen, twenty;
    private static Number[] numbers;
    private static ImageView imageBravo2;
    private static Animation fadeIn;
    private static MediaPlayer mediaPlayer;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        zero = new Number("zero", 0, R.raw.number0, R.color.black_overlay);
        one = new Number("one", 1, R.raw.number1, R.mipmap.one);
        two = new Number("two", 2, R.raw.number2, R.mipmap.two);
        three = new Number("three", 3, R.raw.number3, R.mipmap.three);
        four = new Number("four", 4, R.raw.number4, R.mipmap.four);
        five = new Number("five", 5, R.raw.number5, R.mipmap.five);
        six = new Number("six", 6, R.raw.number6, R.mipmap.six);
        seven = new Number("seven", 7, R.raw.number7, R.mipmap.seven);
        eight = new Number("eight", 8, R.raw.number8, R.mipmap.eight);
        nine = new Number("nine", 9, R.raw.number9, R.mipmap.nine);
        ten = new Number("ten", 10, R.raw.number10, R.mipmap.ten);
        eleven = new Number("eleven", 11, R.raw.number11, R.mipmap.eleven);
        twelve = new Number("twelve", 12, R.raw.number12, R.mipmap.twelve);
        thirteen = new Number("thirteen", 13, R.raw.number13, R.mipmap.thirteen);
        fourteen = new Number("fourteen", 14, R.raw.number14, R.mipmap.fourteen);
        fifteen = new Number("fifteen", 15, R.raw.number15, R.mipmap.fifteen);
        sixteen = new Number("sixteen", 16, R.raw.number16, R.mipmap.sixteen);
        seventeen = new Number("seventeen", 17, R.raw.number17, R.mipmap.seventeen);
        eighteen = new Number("eighteen", 18, R.raw.number18, R.mipmap.eighteen);
        nineteen = new Number("nineteen", 19, R.raw.number19, R.mipmap.nineteen);
        twenty = new Number("twenty", 20, R.raw.number20, R.mipmap.twenty);
        numbers = new Number[]{zero, one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen, fourteen, fifteen, sixteen, seventeen, eighteen, nineteen, twenty};
        imageBravo2 = findViewById(R.id.image_bravo_two);
        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        context = getApplicationContext();
        RecyclerView recyclerView = findViewById(R.id.recycler_view_numbers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.Adapter adapterNumbers = new RecyclerAdapterNumbers(numbers, R.layout.number, context);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterNumbers);
    }

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
                context.startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public static Number[] getNumbers() {
        return numbers;
    }
}
