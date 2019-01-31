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

    private Letter a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;
    private static Letter[] letters;
    private static ImageView imageBravo;
    private static Animation rotate;
    private static MediaPlayer mediaPlayer;
    private static Context context;
    private Button buttonAlphabet;
    private boolean alphabetPlaying;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letters);
        a = new Letter("a", R.raw.sounda, R.mipmap.lettera);
        b = new Letter("b", R.raw.soundb, R.mipmap.letterb);
        c = new Letter("c", R.raw.soundc, R.mipmap.letterc);
        d = new Letter("d", R.raw.soundd, R.mipmap.letterd);
        e = new Letter("e", R.raw.sounde, R.mipmap.lettere);
        f = new Letter("f", R.raw.soundf, R.mipmap.letterf);
        g = new Letter("g", R.raw.soundg, R.mipmap.letterg);
        h = new Letter("h", R.raw.soundh, R.mipmap.letterh);
        i = new Letter("i", R.raw.soundi, R.mipmap.letteri);
        j = new Letter("j", R.raw.soundj, R.mipmap.letterj);
        k = new Letter("k", R.raw.soundk, R.mipmap.letterk);
        l = new Letter("l", R.raw.soundl, R.mipmap.letterl);
        m = new Letter("m", R.raw.soundm, R.mipmap.letterm);
        n = new Letter("n", R.raw.soundn, R.mipmap.lettern);
        o = new Letter("o", R.raw.soundo, R.mipmap.lettero);
        p = new Letter("p", R.raw.soundp, R.mipmap.letterp);
        q = new Letter("q", R.raw.soundq, R.mipmap.letterq);
        r = new Letter("r", R.raw.soundr, R.mipmap.letterr);
        s = new Letter("s", R.raw.sounds, R.mipmap.letterss);
        t = new Letter("t", R.raw.soundt, R.mipmap.lettert);
        u = new Letter("u", R.raw.soundu, R.mipmap.letteru);
        v = new Letter("v", R.raw.soundv, R.mipmap.letterv);
        w = new Letter("w", R.raw.soundw, R.mipmap.letterw);
        x = new Letter("x", R.raw.soundx, R.mipmap.letterx);
        y = new Letter("y", R.raw.soundy, R.mipmap.lettery);
        z = new Letter("z", R.raw.soundz, R.mipmap.letterz);
        letters = new Letter[]{a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z};
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager recyclerManager = new GridLayoutManager(this, 2);
        RecyclerView.Adapter recyclerAdapter = new RecyclerAdapterLetters(letters, R.layout.letter, getApplicationContext());
        buttonAlphabet = findViewById(R.id.button_alphabet);
        context = getApplicationContext();
        imageBravo = findViewById(R.id.image_bravo_rainbow);
        rotate = AnimationUtils.loadAnimation(context, R.anim.rotate_2);
        alphabetPlaying = false;

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(recyclerManager);
        recyclerView.setAdapter(recyclerAdapter);

        //click to play or to stop
        buttonAlphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!alphabetPlaying) {
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
                } else if (alphabetPlaying) {
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
                context.startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public static Letter[] getLetters() {
        return letters;
    }
}
