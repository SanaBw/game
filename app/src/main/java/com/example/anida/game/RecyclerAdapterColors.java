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
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerAdapterColors extends RecyclerView.Adapter<RecyclerAdapterColors.ViewHolder> {

    private Color[] colors;
    private int colorLayout;
    private Context context;

    public RecyclerAdapterColors(Color[] colors, int colorLayout, Context context) {
        this.colors = colors;
        this.colorLayout = colorLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(colorLayout, viewGroup, false);

        return new RecyclerAdapterColors.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterColors.ViewHolder viewHolder, int i) {
        String color = String.valueOf(colors[i].getColor());
        int imageColor = colors[i].getImage();
        boolean isPrimary = colors[i].isPrimary();

        viewHolder.textColor.setText(color);
        viewHolder.imageColor.setImageResource(imageColor);

        if (!isPrimary) {
            int imageMix1 = colors[i].getMixColor1().getImage();
            int imageMix2 = colors[i].getMixColor2().getImage();
            viewHolder.imageMix1.setImageResource(imageMix1);
            viewHolder.imageMix2.setImageResource(imageMix2);
            viewHolder.imageMix1.setVisibility(View.VISIBLE);
            viewHolder.imageMix2.setVisibility(View.VISIBLE);
            viewHolder.textAnd.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imageMix1.setVisibility(View.GONE);
            viewHolder.imageMix2.setVisibility(View.GONE);
            viewHolder.textAnd.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return colors.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textColor, textAnd;
        ImageView imageColor, imageMix1, imageMix2;
        SoundPool soundPool;
        int soundID, colorSound;
        AudioAttributes attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        SharedPreferences sharedPreferences = context.getSharedPreferences("ColorsDone", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Boolean colorPlayed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textColor = itemView.findViewById(R.id.text_color);
            textAnd = itemView.findViewById(R.id.text_and);
            imageColor = itemView.findViewById(R.id.image_color);
            imageMix1 = itemView.findViewById(R.id.image_mix1);
            imageMix2 = itemView.findViewById(R.id.image_mix2);

            imageColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //play sound of color pressed
                    colorSound = colors[getAdapterPosition()].getSound();
                    soundPool = new SoundPool.Builder().setMaxStreams(2).setAudioAttributes(attributes).build();
                    soundID = soundPool.load(context, colorSound, 1);

                    soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                        @Override
                        public void onLoadComplete(final SoundPool soundPool, int sampleId, int status) {
                            soundPool.play(soundID, 1, 1, 1, 0, 1f);

                            //release object after playing the sound
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

                            //add color as played in shared preferences if it is not already added
                            colorPlayed = sharedPreferences.getBoolean(colors[getAdapterPosition()].getColor(), false);
                            if (!colorPlayed) {
                                editor.putBoolean(colors[getAdapterPosition()].getColor(), true);
                                editor.apply();
                            }

                            //if all colors are played and learned - congrats and quiz
                            if (sharedPreferences.getAll().size() == getItemCount()) {
                                editor.clear();
                                editor.apply();
                                ColorsActivity.bravo();
                            }
                        }
                    });
                }
            });
        }
    }
}
