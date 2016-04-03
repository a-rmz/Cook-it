package com.dangerducks.cookit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dangerducks.cookit.kitchen.Recipe;

/**
 * Created by alex on 4/2/16.
 */
public class ShowRecipe extends AppCompatActivity {

    Recipe recipe;
    TextView showName, showDuration, showPortions, showCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);

        recipe = getIntent().getParcelableExtra("recipe");

        showName = (TextView) findViewById(R.id.show_recipe_name);
        showName.setText(recipe.getName());

        showDuration = (TextView) findViewById(R.id.show_duration);
        showDuration.setText(recipe.getDuration() + " minutes");

        showPortions = (TextView) findViewById(R.id.show_recipe_portions);
        showPortions.setText(recipe.getPortions() + " portions");

        showCalories = (TextView) findViewById(R.id.show_recipe_calories);
        showCalories.setText(recipe.getCalories() + " calories");

    }
}
