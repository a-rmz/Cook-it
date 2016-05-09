package com.dangerducks.cookit;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dangerducks.cookit.kitchen.Recipe;
import com.dangerducks.cookit.utils.FileManager;
import com.dangerducks.cookit.utils.RecipeAdapter;

import java.util.Vector;

/**
 * Created by alex on 5/7/16.
 */
public class ShowFavourites extends AppCompatActivity {

    NavigationView navigationView;
    ActionBarDrawerToggle mDrawerToggle;
    CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;

    RecyclerView recyclerView;
    Vector<Recipe> favs;
    RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_favourites);

        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.fav_coordinator_layout);
        recyclerView = (RecyclerView) coordinatorLayout.findViewById(R.id.favourites);
        toolbar = (Toolbar) coordinatorLayout.findViewById(R.id.favs_toolbar);

        favs = new Vector<>();
        favs.addAll(User.user().favourites);

        adapter = new RecipeAdapter(favs);
        recyclerView = (RecyclerView) findViewById(R.id.favourites);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        setupToolbar();
        setUpDrawer();
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


    private void setUpDrawer() {
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.fav_drawer_layout);
        navigationView = (NavigationView) drawerLayout.findViewById(R.id.fav_drawer);

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
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.nav_close_session:
                        FileManager.deleteUserData(ShowFavourites.this);
                        intent = new Intent(ShowFavourites.this, Login.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_favourites:
//                        intent = new Intent(MainActivity.this, ShowFavourites.class);
//                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        Snackbar.make(findViewById(R.id.fav_drawer_layout), "You already here", Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_profile:
                        intent = new Intent(ShowFavourites.this, Profile.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_my_recipes:
                        intent = new Intent(ShowFavourites.this, ShowIngredients.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                }

                return false;
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.fav_drawer);
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

        getSupportActionBar().setTitle(getResources().getString(R.string.favourites));

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
}
