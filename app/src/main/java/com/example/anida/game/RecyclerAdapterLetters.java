package com.example.anida.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Random;

public class RecyclerAdapterLetters extends RecyclerView.Adapter<RecyclerAdapterLetters.ViewHolder> {

    private String[] items;
    private int[] itemsImages;
    private int itemLayout;
    private int[] itemsSounds;
    private Context context;


    public RecyclerAdapterLetters(String[] items, int[] itemsImages, int itemLayout, int[] itemsSounds, Context context) {
        this.items = items;
        this.itemsImages = itemsImages;
        this.itemLayout = itemLayout;
        this.itemsSounds = itemsSounds;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int itemImage = itemsImages[position];
        holder.image.setImageResource(itemImage);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        int itemSound;
        ImageView image;
        int[] animationResources = {R.anim.bounce_down, R.anim.bounce_up, R.anim.bounce_left, R.anim.bounce_right};
        SharedPreferences sharedPreferences = context.getSharedPreferences("LettersDone", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Boolean letterPlayed;
        Animation animation;
        SoundPool soundPool;
        int soundID;
        AudioAttributes attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();

        private ViewHolder(final View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //randomize animation
                    Random random = new Random();
                    random.nextInt();
                    int randomAnimationResource = animationResources[random.nextInt(animationResources.length)];
                    animation = AnimationUtils.loadAnimation(context, randomAnimationResource);
                    image.startAnimation(animation);

                    //play sound of letter pressed
                    itemSound = itemsSounds[getAdapterPosition()];
                    soundPool = new SoundPool.Builder().setMaxStreams(10)
                            .setAudioAttributes(attributes)
                            .build();

                    soundID = soundPool.load(context, itemSound, 1);
                    soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                        @Override
                        public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                            soundPool.play(soundID, 1, 1, 1, 0, 1f);

                            //release sound after each letter (around 3000ms)
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(3000);
                                        soundPool.release();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                            //add as played in shared preferences if it is not already added
                            letterPlayed = sharedPreferences.getBoolean(items[getAdapterPosition()], false);
                            if (!letterPlayed) {
                                editor.putBoolean(items[getAdapterPosition()], true);
                                editor.apply();
                            }

                            if (sharedPreferences.getAll().size() == 26) {
                                editor.clear();
                                editor.apply();
                                LettersActivity.bravo();
                            }
                        }
                    });
                }
            });


        }

    }


}


