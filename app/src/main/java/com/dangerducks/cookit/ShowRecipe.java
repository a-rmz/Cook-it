package com.dangerducks.cookit;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

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

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.inflateMenu(R.menu.show_recipe_actions);

        // Home menu for header
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case android.R.id.home:
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
        FileManager.deleteRecipe(recipe, getFilesDir().toString());
        setResult(-1);
        finish();
    }
}
