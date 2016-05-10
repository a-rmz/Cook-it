package com.dangerducks.cookit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dangerducks.cookit.kitchen.Recipe;
import com.dangerducks.cookit.utils.FileManager;
import com.dangerducks.cookit.utils.StepAdapter;

/**
 * Created by alex on 4/2/16.
 */
public class ShowRecipe extends AppCompatActivity {

    Recipe recipe;
    TextView showDuration, showPortions, showCalories;
    RecyclerView recyclerView;
    AppBarLayout appBar;
    CollapsingToolbarLayout collapsingToolbar;
    NestedScrollView nested;
    Toolbar toolbar;
    FloatingActionButton favourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);

        recipe = (Recipe) getIntent().getExtras().get("recipe");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_step_container);

        recyclerView.setNestedScrollingEnabled(false);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.recipe_collapsing_toolbar);
        collapsingToolbar.setTitle(recipe.getName());
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);

        appBar = (AppBarLayout) findViewById(R.id.app_bar_layout);

        toolbar = (Toolbar) findViewById(R.id.recipe_toolbar);
        setupToolbar();

        showDuration = (TextView) findViewById(R.id.show_duration);
        showDuration.setText(recipe.preparation.getPreparationTime() + " minutes");

        showPortions = (TextView) findViewById(R.id.show_recipe_portions);
        showPortions.setText(recipe.getPortions() + " portions");

        showCalories = (TextView) findViewById(R.id.show_recipe_calories);
        showCalories.setText(recipe.getCalories() + " calories");

        setupRecyclerView();

        favourite = (FloatingActionButton) findViewById(R.id.recipe_favourite);
        changeFABColor(recipe.isFavourite());
        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t;
                FileManager.deleteRecipe(recipe, getFilesDir().toString());
                if(recipe.isFavourite()) {
                    recipe.unfav();
                    User.user().removeFavouriteRecipe(recipe);
                    t = Toast.makeText(getApplicationContext(), R.string.unfav, Toast.LENGTH_SHORT);

                } else {
                    recipe.fav();
                    User.user().addFavouriteRecipe(recipe);
                    t = Toast.makeText(getApplicationContext(), R.string.fav, Toast.LENGTH_SHORT);
                }

                changeFABColor(recipe.isFavourite());

                User.user().updateRecipe(recipe, User.user().recipesSaved.indexOf(recipe));
                FileManager.saveRecipe(recipe, getFilesDir().toString());
                t.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 25);
                t.show();
                setResult(-2);
            }
        });

    }

    protected void setupRecyclerView() {
        StepAdapter adapter = new StepAdapter(recipe.preparation.getSteps());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.inflateMenu(R.menu.show_recipe_actions);

        // Home menu for header
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    private void changeFABColor(boolean state) {
        if(state) {
            favourite.setBackgroundTintList(getResources().getColorStateList(R.color.loginBackground));
        } else {
            favourite.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case android.R.id.home:
                setResult(0);
                finish();
                return true;
            case R.id.action_search:
                return true;
            case R.id.action_remove:
                removeRecipe();
                return true;
            case R.id.action_credits:
                AboutDialog aboutDialog = new AboutDialog(this);
                aboutDialog.setTitle(getResources().getString(R.string.about));
                aboutDialog.show();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.show_recipe_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    protected void removeRecipe() {
        User.user().recipesSaved.removeElement(recipe);
        System.out.println("rmvusz: " + User.user().recipesSaved.size());
        FileManager.deleteRecipe(recipe, getFilesDir().toString());
        setResult(-1);
        finish();
    }
}
