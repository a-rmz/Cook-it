package com.dangerducks.cookit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.dangerducks.cookit.kitchen.Recipe;
import com.dangerducks.cookit.utils.StepAdapter;

/**
 * Created by alex on 4/2/16.
 */
public class ShowRecipe extends AppCompatActivity {

    Recipe recipe;
    TextView showName, showDuration, showPortions, showCalories;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);

        recipe = (Recipe) getIntent().getExtras().get("recipe");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_step_container);

        showName = (TextView) findViewById(R.id.show_recipe_name);
        showName.setText(recipe.getName());

        showDuration = (TextView) findViewById(R.id.show_duration);
        showDuration.setText(recipe.getDuration() + " minutes");

        showPortions = (TextView) findViewById(R.id.show_recipe_portions);
        showPortions.setText(recipe.getPortions() + " portions");

        showCalories = (TextView) findViewById(R.id.show_recipe_calories);
        showCalories.setText(recipe.getCalories() + " calories");

        setupRecyclerView();

    }

    protected void setupRecyclerView() {
        StepAdapter adapter = new StepAdapter(recipe.preparation.getSteps());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
