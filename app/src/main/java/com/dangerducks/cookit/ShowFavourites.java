package com.dangerducks.cookit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dangerducks.cookit.kitchen.Recipe;
import com.dangerducks.cookit.utils.RecipeAdapter;

import java.util.Vector;

/**
 * Created by alex on 5/7/16.
 */
public class ShowFavourites extends AppCompatActivity {

    RecyclerView recyclerView;
    Vector<Recipe> favs;
    RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_favourites);

        favs = new Vector<>();
        favs.addAll(User.user().favourites);

        adapter = new RecipeAdapter(favs);
        recyclerView = (RecyclerView) findViewById(R.id.favourites);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == -2) {
            favs.removeAllElements();
            favs.addAll(User.user().favourites);
            adapter.notifyDataSetChanged();
        }
    }
}
