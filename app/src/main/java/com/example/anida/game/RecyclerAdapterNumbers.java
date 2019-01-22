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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerAdapterNumbers extends RecyclerView.Adapter<RecyclerAdapterNumbers.ViewHolder> {

    private String[] items;
    private int[] itemsImages;
    private int itemLayout;
    private int[] itemsSounds;
    private Context context;
    private String[] itemsString;

    public RecyclerAdapterNumbers(String[] items, String[] itemsString, int[] itemsSounds, int[] itemsImages, int itemLayout, Context context) {
        this.items = items;
        this.itemsString = itemsString;
        this.itemsSounds = itemsSounds;
        this.itemsImages = itemsImages;
        this.itemLayout = itemLayout;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        return new RecyclerAdapterNumbers.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String number = items[i];
        String numberString = itemsString[i];
        int numberImage = itemsImages[i];

        viewHolder.textNumberString.setText(numberString);
        viewHolder.textNumber.setText(number);

        viewHolder.imageNumber.setImageResource(numberImage);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNumber, textNumberString;
        ImageView imageNumber;
        Button buttonSound;
        SoundPool soundPool;
        int soundID, itemSound;
        AudioAttributes attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        SharedPreferences sharedPreferences = context.getSharedPreferences("NumbersDone", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Boolean numberPlayed;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNumber = itemView.findViewById(R.id.text_number);
            textNumberString = itemView.findViewById(R.id.text_number_string);
            imageNumber = itemView.findViewById(R.id.image_number);
            buttonSound = itemView.findViewById(R.id.button_sound);

            buttonSound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //play sound of letter pressed
                    itemSound = itemsSounds[getAdapterPosition()];
                    soundPool = new SoundPool.Builder().setMaxStreams(2)
                            .setAudioAttributes(attributes)
                            .build();

                    soundID = soundPool.load(context, itemSound, 1);
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

                            //add number as played in shared preferences if it is not already added
                            numberPlayed = sharedPreferences.getBoolean(items[getAdapterPosition()], false);
                            if (!numberPlayed) {
                                editor.putBoolean(items[getAdapterPosition()], true);
                                editor.apply();
                            }

                            if (sharedPreferences.getAll().size() > 0) {
                                editor.clear();
                                editor.apply();
                                NumbersActivity.bravo();
                            }
                        }
                    });


                }
            });
        }
    }
}
