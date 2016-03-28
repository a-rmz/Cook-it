package com.dangerducks.cookit;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dangerducks.cookit.utils.FileManager;


/**
 * Created by alex on 2/29/16.
 */
public class MainActivity extends AppCompatActivity{

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    Toolbar toolbar;
    private ImageView taco;
    private FloatingActionButton fab;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setupToolbar();

        drawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);
        setUpDrawer();

    }


    private void setUpDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.main_drawer);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer) {
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                getSupportActionBar().setTitle(R.string.open_drawer);
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

                switch (menuItem.getItemId()) {
                    case R.id.nav_close_session:
                        FileManager.deleteUserData(MainActivity.this);
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        startActivity(intent);
                        finish();
                        break;
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);

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

    public void nothingToDoHere() {
        Snackbar.make(findViewById(R.id.main_drawer_layout), "I'm a Snackbar", Snackbar.LENGTH_LONG).show();
    }

    private void addRecipe() {
        Intent intent = new Intent(MainActivity.this, AddRecipe.class);
        startActivity(intent);
    }
}

