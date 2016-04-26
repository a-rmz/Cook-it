package com.dangerducks.cookit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import com.github.clans.fab.FloatingActionMenu;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.dangerducks.cookit.kitchen.Recipe;
import com.dangerducks.cookit.utils.RecipeAdapter;

import java.util.Vector;


/**
 * Created by alex on 2/29/16.
 */
public class MainActivity extends AppCompatActivity{

    private static final int RECIPE_ACTIVITY = 1;
    private static final int RECIPE_REMOVED = -1;


    private CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;
    private com.github.clans.fab.FloatingActionMenu fab;
    private com.github.clans.fab.FloatingActionButton fab_recipe, fab_ingredient;
    RecyclerView recyclerView;
    Vector<Recipe> displayableRecipes;
    RecipeAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.main_coordinator_layout);
        recyclerView = (RecyclerView) coordinatorLayout.findViewById(R.id.card_recipe_container);
        toolbar = (Toolbar) coordinatorLayout.findViewById(R.id.toolbar);
        fab = (com.github.clans.fab.FloatingActionMenu) coordinatorLayout.findViewById(R.id.fab);

        fab_ingredient = (com.github.clans.fab.FloatingActionButton) coordinatorLayout.findViewById(R.id.fab_ingredient);
        fab_recipe = (com.github.clans.fab.FloatingActionButton) coordinatorLayout.findViewById(R.id.fab_recipe);

        fab_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe();
                fab.close(false);
            }
        });

        setupToolbar();


        displayableRecipes = User.user().recipesSaved;
        adapter = new RecipeAdapter(displayableRecipes);
        setupSwiper();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRecyclerView();
    }


    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.inflateMenu(R.menu.main_activity_actions);

        // Home menu for header
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.action_search:
                nothingToDoHere();
                return true;
            case R.id.action_credits:
                AboutDialog aboutDialog = new AboutDialog(this);
                aboutDialog.setTitle(getResources().getString(R.string.about));
                aboutDialog.show();
                return true;
            case R.id.action_settings:
                nothingToDoHere();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void nothingToDoHere() {
        Snackbar.make(findViewById(R.id.main_coordinator_layout), "I'm a Snackbar", Snackbar.LENGTH_LONG).show();
    }

    private void addRecipe() {
        Intent intent = new Intent(MainActivity.this, AddRecipe.class);
        startActivity(intent);
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setupSwiper() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Snackbar.make(coordinatorLayout, "Done", Snackbar.LENGTH_LONG).show();
                adapter.remove(viewHolder.getAdapterPosition());
            }

        };
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RECIPE_ACTIVITY:
                if(resultCode == RECIPE_REMOVED) //setupRecyclerView();
                    displayableRecipes = User.user().recipesSaved;
                    adapter = new RecipeAdapter(displayableRecipes);
                break;
        }
    }
}

