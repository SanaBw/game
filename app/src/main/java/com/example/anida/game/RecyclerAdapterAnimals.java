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

public class RecyclerAdapterAnimals extends RecyclerView.Adapter<RecyclerAdapterAnimals.AnimalViewHolder> {

    private Animal[] animals;
    private int animalsLayout;
    private Context context;


    public RecyclerAdapterAnimals(Animal[] animals, int animalsLayout, Context context) {
        this.animals = animals;
        this.animalsLayout = animalsLayout;
        this.context = context;

    }


    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(animalsLayout, parent, false);
        AnimalViewHolder holder = new AnimalViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int i) {
        holder.bindAnimal(animals[i]);

    }

    @Override
    public int getItemCount() {
        return animals.length;
    }


    public class AnimalViewHolder extends RecyclerView.ViewHolder {
        TextView textAnimal;
        Button buttonSound;
        ImageView imageAnimal;
        SoundPool soundPool;
        int soundID, animalSound;
        AudioAttributes attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        SharedPreferences sharedPreferences = context.getSharedPreferences("AnimalsDone", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Boolean animalPlayed;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            textAnimal = itemView.findViewById(R.id.text_animal);
            buttonSound = itemView.findViewById(R.id.button_sound);
            imageAnimal = itemView.findViewById(R.id.image_animal);
        }

        public void bindAnimal(final Animal animal) {
            textAnimal.setText(animal.getName());
            imageAnimal.setImageResource(animal.getPicture());

            buttonSound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animalSound = animal.getPronunciation();
                    soundPool = new SoundPool.Builder().setMaxStreams(2)
                            .setAudioAttributes(attributes)
                            .build();

                    soundID = soundPool.load(context, animalSound, 1);
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

                            //add animal as learned in shared preferences if it is not already added
                            animalPlayed = sharedPreferences.getBoolean(animal.getName(), false);
                            if (!animalPlayed) {
                                editor.putBoolean(animal.getName(), true);
                                editor.apply();
                                System.out.println(sharedPreferences.getAll().size());
                            }

                            //if all animal pronunciations are played and learned - congrats and quiz
                            if (sharedPreferences.getAll().size() == getItemCount()) {
                                editor.clear();
                                editor.apply();
                                //AnimalsActivity.bravo();
                                System.out.println("BRAVOOO");
                            }

                        }
                    });
                }
            });

                imageAnimal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        animalSound = animal.getSound();
                        soundPool = new SoundPool.Builder().setMaxStreams(2)
                                .setAudioAttributes(attributes)
                                .build();

                        soundID = soundPool.load(context, animalSound, 1);
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
                            }
                        });
                    }
                });
            }

        }
    }


