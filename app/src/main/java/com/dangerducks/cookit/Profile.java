package com.dangerducks.cookit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by alex on 5/8/16.
 */
public class Profile extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        TextView name = (TextView) findViewById(R.id.profile_name);
        TextView user = (TextView) findViewById(R.id.profile_user);
        TextView mail = (TextView) findViewById(R.id.profile_mail);

        name.setText(User.user().name + " " + User.user().lastName);
        user.setText(User.user().getUsername());
        mail.setText(User.user().getEmail());

        toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        setupToolbar();

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Home menu for header
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle(getResources().getString(R.string.profile));

    }


}
