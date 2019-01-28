package com.example.anida.game;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class AnimalsActivity extends AppCompatActivity {

    private Animal cat, dog, bird, chicken, duck, cow, horse, butterfly, rabbit, bear, penguin, lion, monkey;
    private Animal[] animals;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);

        cat = new Animal("cat", R.raw.meow, R.raw.cat, R.mipmap.cat);
        dog = new Animal("dog", R.raw.bark, R.raw.dog, R.mipmap.dog);
        bird = new Animal("bird", R.raw.whistle, R.raw.bird, R.mipmap.bird);
        chicken = new Animal("chicken", R.raw.chickensound, R.raw.chicken, R.mipmap.chicken);
        duck = new Animal("duck", R.raw.quack, R.raw.duck, R.mipmap.duck);
        cow = new Animal("cow", R.raw.moo, R.raw.cow, R.mipmap.cow);
        horse = new Animal("horse", R.raw.neigh, R.raw.horse, R.mipmap.horse);
        butterfly = new Animal("butterfly", R.raw.nosound, R.raw.butterfly, R.mipmap.butterfly);
        rabbit = new Animal("rabbit", R.raw.nosound, R.raw.rabbit, R.mipmap.rabbit);
        bear = new Animal("bear", R.raw.nosound, R.raw.bear, R.mipmap.bear);
        penguin = new Animal("penguin", R.raw.nosound, R.raw.penguin, R.mipmap.penguin);
        lion = new Animal("lion", R.raw.roar, R.raw.lion, R.mipmap.lion);
        monkey = new Animal("monkey", R.raw.monkeysound, R.raw.monkey, R.mipmap.monkey);
        animals = new Animal[]{cat,dog, bird, chicken, duck, cow, horse, butterfly, rabbit, bear, penguin, lion, monkey};
        context=getApplicationContext();

        RecyclerView recyclerView = findViewById(R.id.recycler_view_animals);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapterAnimals = new RecyclerAdapterAnimals(animals, R.layout.animal, context);
        recyclerView.setAdapter(adapterAnimals);



    }
}
