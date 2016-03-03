package com.dangerducks.cookit;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.*;
import android.widget.*;

public class Login extends AppCompatActivity {

    private String[] planets;
    private DrawerLayout drawerLayout;
    private ListView listView;
    private Button login;

    Toolbar toolbar;

    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        // Inflate toolbar
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_media_play); // Change icon
        toolbar.inflateMenu(R.menu.main_activity_actions);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        //View drawer = getLayoutInflater().inflate(R.layout.drawer, null);

        // Home menu for drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setUpDrawer();

        login = (Button) findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginValidation();
            }
        });
    }

    protected void loginValidation() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    protected void onRestart() {
        super.onRestart();
        EditText clear = (EditText) findViewById(R.id.usr);
        clear.setText("");
        clear.requestFocus();
        clear = (EditText) findViewById(R.id.pass);
        clear.setText("");

    }

    private void setUpDrawer() {
        planets = getResources().getStringArray(R.array.drawer_options);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.left_drawer);

        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, planets));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

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
                nothingToDoHere();
                return true;
            case R.id.action_settings:
                nothingToDoHere();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void nothingToDoHere() {
        Snackbar.make(findViewById(R.id.drawer_layout), "I'm a Snackbar", Snackbar.LENGTH_SHORT).show();
    }
}