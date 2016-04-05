package com.dangerducks.cookit;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dangerducks.cookit.DB.DBFunct;
import com.dangerducks.cookit.utils.FileManager;

/**
 * Created by alex on 3/21/16.
 */
public class SignIn extends AppCompatActivity {

    Button btn;
    EditText usr, pass, mail;
    DBFunct db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_layout);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        db = new DBFunct();

        btn = (Button) findViewById(R.id.signin);

        usr = (EditText) findViewById(R.id.usr_sign);
        pass = (EditText) findViewById(R.id.pass_sign);
        mail = (EditText) findViewById(R.id.email_sign);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usr.getText().toString();
                String password = pass.getText().toString();
                String email = mail.getText().toString();

                if(validate(username, password, email)) {
                    FileManager.saveUserData(SignIn.this, username, password, email);
                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Snackbar.make(findViewById(R.id.signin_layout), getResources().getString(R.string.something_wrong), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validate(String username, String password, String email) {
        if(
            validateUsername(username) &&
            validatePassword(password) &&
            validateEmail(email)
          ) {
            User.user().setUsername(username);
            User.user().setEmail(email);
            return db.RegisterUser(username, password, email, "");
        }
        return false;
    }

    private boolean validateUsername(String user) {
        if(user.length() > 0 && user.length() <= 30) return true;
        else return false;
    }

    private boolean validatePassword(String pass) {
        if(!pass.isEmpty() && pass.length() <= 25) return true;
        return false;
    }

    private boolean validateEmail(String email) {
        if(email.contains("@")) return true;
        else return false;
    }

}
