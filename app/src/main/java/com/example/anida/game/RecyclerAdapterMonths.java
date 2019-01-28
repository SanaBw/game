package com.example.anida.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.Image;
import android.media.SoundPool;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

class RecyclerAdapterMonths extends RecyclerView.Adapter<RecyclerAdapterMonths.ViewHolder> {

    int[] monthsDataset;
    String[] monthsDatasetString;
    String[] monthsSeasons;
    int[] monthsSounds;
    int[] monthsImages;
    int monthLayout;
    Context context;
    RecyclerView recyclerView;

    public RecyclerAdapterMonths(int[] monthsDataset, String[] monthsDatasetString, String[] monthsSeasons, int[] monthsSounds, int[] monthsImages, int monthLayout, Context context) {
        this.monthsDataset = monthsDataset;
        this.monthsDatasetString = monthsDatasetString;
        this.monthsSeasons = monthsSeasons;
        this.monthsSounds = monthsSounds;
        this.monthsImages = monthsImages;
        this.monthLayout = monthLayout;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(monthLayout, viewGroup, false);
        return new RecyclerAdapterMonths.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapterMonths.ViewHolder viewHolder, int i) {
        final int monthNumber = monthsDataset[i];
        final String season = monthsSeasons[i];
        int monthImage = monthsImages[i];

        viewHolder.textMonth.setText(Integer.toString(monthNumber) + " " + season);
        viewHolder.imageMonth.setImageResource(monthImage);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

       this.recyclerView = recyclerView;
    }


    @Override
    public int getItemCount() {
        return monthsDataset.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textMonth;
        ImageView imageMonth;
        SharedPreferences sharedPreferences = context.getSharedPreferences("MonthsDone", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Boolean monthPlayed;
        SoundPool soundPool;
        int soundID;
        AudioAttributes attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textMonth = itemView.findViewById(R.id.text_month);
            imageMonth = itemView.findViewById(R.id.image_month);

            imageMonth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //play sound of letter pressed
                    soundPool = new SoundPool.Builder().setMaxStreams(10)
                            .setAudioAttributes(attributes)
                            .build();

                    soundID = soundPool.load(context, monthsSounds[getAdapterPosition()], 1);
                    soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                        @Override
                        public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                            soundPool.play(soundID, 1, 1, 1, 0, 1f);

                            //release sound after each month (around 3000ms)
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
                            monthPlayed = sharedPreferences.getBoolean(monthsDatasetString[getAdapterPosition()], false);
                            if (!monthPlayed) {
                                editor.putBoolean(monthsDatasetString[getAdapterPosition()], true);
                                editor.apply();
                            }

                            if (sharedPreferences.getAll().size() == getItemCount()) {
                                editor.clear();
                                editor.apply();
                                MonthsActivity.bravo();
                            }
                        }
                    });
                }
            });

        }
    }
}
