package com.dangerducks.cookit;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dangerducks.cookit.kitchen.Category;
import com.dangerducks.cookit.kitchen.Recipe;
import com.dangerducks.cookit.kitchen.Step;
import com.dangerducks.cookit.utils.FileManager;

/**
 * Created by alex on 3/25/16.
 */
public class AddRecipe extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    Spinner categories;
    int stepsAdded = 0;

    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipe);

        toolbar = (Toolbar) findViewById(R.id.add_toolbar);
        setupToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.add_drawer_layout);
        setupDrawer();

        categories = (Spinner) findViewById(R.id.categories);
        setupSpinner();

        setupStepAdder();

        recipe = new Recipe();
    }

    private void setupDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.add_drawer);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer) {
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_close_session:
                        FileManager.deleteUserData(AddRecipe.this);
                        Intent intent = new Intent(AddRecipe.this, Login.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return false;
            }
        });

        navigationView = (NavigationView) findViewById(R.id.add_drawer);
        View header = navigationView.getHeaderView(0);

        TextView tv = (TextView) header.findViewById(R.id.user_drawer);
        tv.setText(User.user().getUsername());

        tv = (TextView) header.findViewById(R.id.email_drawer);
        tv.setText(User.user().getEmail());


    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        toolbar.inflateMenu(R.menu.add_recipe_actions);

        // Home menu for header
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(adapter);
        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupStepAdder() {
        Button add = (Button) findViewById(R.id.add_step_btn);
        final LinearLayout container = (LinearLayout) findViewById(R.id.step_container);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText) findViewById(R.id.add_step);

                LayoutInflater inflater = (LayoutInflater) AddRecipe.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.add_recipe_step, null);

                Step step = new Step();

                TextView textOut = (TextView) view.findViewById(R.id.step);
                if (text.getText().toString().length() == 0) {
                    Snackbar.make(v, getResources().getString(R.string.empty_step), Snackbar.LENGTH_LONG).show();
                    return;
                }
                step.setDescription(text.getText().toString());
                textOut.setText(step.getDescription());

                Button remove = (Button) view.findViewById(R.id.remove_step_btn);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout) view.getParent()).removeView(view);
                        recipe.removeStep(--stepsAdded);
                    }
                });

                container.addView(view);
                stepsAdded++;

                addIngredients(step.getDescription());
                text.setText("");

                textOut = (TextView) findViewById(R.id.duration);
                textOut.setText(recipe.preparation.getPreparationTime() + "");
            }
        });


    }

    private void addIngredients(String step) {
        AddStep addStep = new AddStep(this, step, recipe);
        addStep.setTitle(step);
        addStep.setCancelable(false);
        addStep.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_recipe_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (mDrawerToggle.onOptionsItemSelected(menuItem)) {
            return true;
        }
        switch (menuItem.getItemId()) {
            case R.id.action_save:
                saveRecipe();
                return true;
            case R.id.action_clear:
                clearRecipe();
                return true;
            case R.id.action_delete:
                goBack();
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

    private void clearRecipe() {
        TextView textView = (TextView) findViewById(R.id.recipe_name);
        textView.setText("");

        textView = (TextView) findViewById(R.id.portions);
        textView.setText("");

        textView = (TextView) findViewById(R.id.calories);
        textView.setText("");

        ((LinearLayout) findViewById(R.id.step_container)).removeAllViews();
    }

    private void goBack() {
        finish();
    }

    private void saveRecipe() {
        String name = ((TextView) findViewById(R.id.recipe_name)).getText().toString();
        String portions = ((TextView) findViewById(R.id.portions)).getText().toString();
        String calories = ((TextView) findViewById(R.id.calories)).getText().toString();
        int category = (int) categories.getSelectedItemId();

        if(name.isEmpty() || portions.isEmpty() || calories.isEmpty() || stepsAdded == 0 || category == 0) {
            Snackbar.make(findViewById(R.id.add_drawer_layout), getResources().getString(R.string.empty_recipe), Snackbar.LENGTH_LONG).show();
        } else {
            recipe.setName(name);
            recipe.setPortions(Integer.parseInt(portions));
            recipe.setCalories(Integer.parseInt(calories));
            recipe.setCategory(new Category(categories.getSelectedItem().toString()));

            recipe.setRID();
            FileManager.saveRecipe(recipe, getFilesDir().getPath());
            User.user().recipesSaved.add(recipe);
            Snackbar.make(findViewById(R.id.add_drawer_layout), getResources().getString(R.string.recipe_saved), Snackbar.LENGTH_LONG).show();
            goBack();
        }

    }

    public void nothingToDoHere() {
        Snackbar.make(findViewById(R.id.add_drawer_layout), "These are not the droids you're looking for...", Snackbar.LENGTH_LONG).show();
    }
}
