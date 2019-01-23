package com.example.anida.game;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class LettersActivity extends AppCompatActivity {

    static ImageView imageBravo;
    static Animation rotate;
    static String[] lettersDataset;
    static int[] lettersSounds, lettersImages;
    private static MediaPlayer mediaPlayer;
    private static Context context;
    private Button buttonAlphabet;
    private boolean alphabetPlaying;

    public static void bravo() {
        imageBravo.setAlpha(1f); //because if it's second time to play the animation, alpha is 0f (onAnimationEnd)
        imageBravo.startAnimation(rotate);
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageBravo.setVisibility(View.VISIBLE);
                mediaPlayer = MediaPlayer.create(context, R.raw.claps);
                mediaPlayer.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageBravo.setAlpha(0f); //hide after animation ends
                if (mediaPlayer != null) {
                    try {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //start the quiz
                Intent intent = new Intent(context, LettersQuiz.class);
                intent.putExtra("lettersDataset", lettersDataset);
                intent.putExtra("lettersSounds", lettersSounds);
                context.startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letters);

        lettersDataset = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        lettersImages = new int[]{R.mipmap.lettera, R.mipmap.letterb, R.mipmap.letterc, R.mipmap.letterd, R.mipmap.lettere, R.mipmap.letterf, R.mipmap.letterg,
                R.mipmap.letterh, R.mipmap.letteri, R.mipmap.letterj, R.mipmap.letterk, R.mipmap.letterl, R.mipmap.letterm, R.mipmap.lettern, R.mipmap.lettero,
                R.mipmap.letterp, R.mipmap.letterq, R.mipmap.letterr, R.mipmap.letterss, R.mipmap.lettert, R.mipmap.letteru, R.mipmap.letterv, R.mipmap.letterw,
                R.mipmap.letterx, R.mipmap.lettery, R.mipmap.letterz};
        lettersSounds = new int[]{R.raw.sounda, R.raw.soundb, R.raw.soundc, R.raw.soundd, R.raw.sounde, R.raw.soundf, R.raw.soundg, R.raw.soundh, R.raw.soundi,
                R.raw.soundj, R.raw.soundk, R.raw.soundl, R.raw.soundm, R.raw.soundn, R.raw.soundo, R.raw.soundp, R.raw.soundq, R.raw.soundr, R.raw.sounds,
                R.raw.soundt, R.raw.soundu, R.raw.soundv, R.raw.soundw, R.raw.soundx, R.raw.soundy, R.raw.soundz};

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        buttonAlphabet = findViewById(R.id.button_alphabet);
        context = getApplicationContext();
        imageBravo = findViewById(R.id.image_bravo);
        rotate = AnimationUtils.loadAnimation(context, R.anim.rotate_2);
        alphabetPlaying=false;

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(recyclerManager);
        RecyclerView.Adapter recyclerAdapter = new RecyclerAdapterLetters(lettersDataset, lettersImages, R.layout.letter, lettersSounds, getApplicationContext());
        recyclerView.setAdapter(recyclerAdapter);

        buttonAlphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!alphabetPlaying){
                    mediaPlayer = MediaPlayer.create(context, R.raw.alphabet);
                    mediaPlayer.start();
                    alphabetPlaying = true;
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            alphabetPlaying = false;
                        }
                    });
                } else if(alphabetPlaying){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    alphabetPlaying = false;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //turn off all music if back is pressed
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                mediaPlayer.release();
                alphabetPlaying = false;
            } catch (Exception e) {
                super.onBackPressed();
            }
        }
        super.onBackPressed();
    }
}
