package com.dangerducks.cookit;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;

import com.dangerducks.cookit.DB.local.IngredientContract;
import com.dangerducks.cookit.DB.local.LocalDBOperations;
import com.dangerducks.cookit.utils.FileManager;
import com.github.clans.fab.FloatingActionMenu;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dangerducks.cookit.kitchen.Recipe;
import com.dangerducks.cookit.utils.RecipeAdapter;
import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;


/**
 * Created by alex on 2/29/16.
 */
public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final int RECIPE_ACTIVITY = 1;
    private static final int RECIPE_REMOVED = -1;
    private static final int RECIPE_FAVED = -2;


    private CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;
    private com.github.clans.fab.FloatingActionMenu fab;
    private com.github.clans.fab.FloatingActionButton fab_recipe, fab_ingredient;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    RecyclerView recyclerView;
    NavigationView navigationView;
    Vector<Recipe> displayableRecipes;
    RecipeAdapter adapter;
    SearchView searchView;
    MenuItem searchItem;


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

        fab_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.close(false);
                AddIngredientDialog addIngredientDialog = new AddIngredientDialog(v.getContext());
                addIngredientDialog.setTitle(getResources().getString(R.string.add_ingredient_title));
                addIngredientDialog.show();
            }
        });

        fab_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe();
                fab.close(false);
            }
        });

        setupToolbar();
        setUpDrawer();


        displayableRecipes = User.user().recipesSaved;
        adapter = new RecipeAdapter(displayableRecipes);
        setupSwiper();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRecyclerView();
    }

    private void setUpDrawer() {
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        navigationView = (NavigationView) drawerLayout.findViewById(R.id.main_drawer);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer) {
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.nav_close_session:
                        FileManager.deleteUserData(MainActivity.this);
                        intent = new Intent(MainActivity.this, Login.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_favourites:
                        intent = new Intent(MainActivity.this, ShowFavourites.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_profile:
                        intent = new Intent(MainActivity.this, Profile.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_settings:
                        User.user().recipesSaved.clear();
                        adapter.clear();
                        FileManager.clearRecipes(getFilesDir().getPath());
                        drawerLayout.closeDrawers();
                        Snackbar.make(findViewById(R.id.main_drawer_layout), "Recipes deleted", Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_my_recipes:
                        intent = new Intent(MainActivity.this, ShowIngredients.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                }

                return false;
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.main_drawer);
        View header = navigationView.getHeaderView(0);

        TextView tv = (TextView) header.findViewById(R.id.user_drawer);
        tv.setText(User.user().getUsername());

        tv = (TextView) header.findViewById(R.id.email_drawer);
        tv.setText(User.user().getEmail());


    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.inflateMenu(R.menu.main_activity_actions);

        // Home menu for header
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

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


        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo((
                searchManager.getSearchableInfo(
                        new ComponentName(this, MainActivity.class)
                )));

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
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
        adapter.notifyDataSetChanged();
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
                Snackbar.make(recyclerView, "Done", Snackbar.LENGTH_LONG).show();
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
                if(resultCode == RECIPE_REMOVED) {//setupRecyclerView();
//                    User.user().recipesSaved = data.getExtras().get("recipes");
//                    displayableRecipes = User.user().recipesSaved;
//                    System.out.println("drsz: " + displayableRecipes.size());
//                    adapter.update(displayableRecipes);
                } else
                if(resultCode == RECIPE_FAVED) {
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        MenuItemCompat.collapseActionView(searchItem);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<Recipe> found = new ArrayList<>();
        for(Recipe r : User.user().recipesSaved) {
            if(r.getName().toLowerCase().contains(newText.toLowerCase()) && newText.length() > 0) {
                found.add(r);
            }
        }

        // Cursor
        String[] columns = new String[] { "_id", "text", "int" };
        Object[] temp = new Object[] { 0, "default", 0 };

        MatrixCursor cursor = new MatrixCursor(columns);

        for(int i = 0; i < found.size(); i++) {

            temp[0] = i;
            temp[1] = found.get(i).getName();
            temp[2] = found.get(i).getPortions();
            cursor.addRow(temp);

        }

        // SearchView

        searchView.setSuggestionsAdapter(new CursorAdapter(this, cursor, false) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.recipe_card, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView tv = (TextView) view.findViewById(R.id.card_header);
                tv.setText(cursor.getString(1));
                tv = (TextView) view.findViewById(R.id.card_show_portions);
                tv.setText(cursor.getInt(2) + " portions");

                Recipe rec = null;
                for(Recipe r : User.user().recipesSaved) {
                    if(r.getName().equals(cursor.getString(1))) {
                        rec = r;
                    }
                }
                final Recipe recipe = rec;

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), ShowRecipe.class);
                        intent.putExtra("recipe", recipe);
                        searchView.clearFocus();
                        MenuItemCompat.collapseActionView(searchItem);
                        ((Activity) v.getContext()).startActivityForResult(intent, 1, null);
                    }
                });
            }
        });

        return false;
    }
}

