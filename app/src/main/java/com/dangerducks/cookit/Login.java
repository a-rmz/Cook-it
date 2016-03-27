package com.dangerducks.cookit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.dangerducks.cookit.utils.FileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;

public class Login extends AppCompatActivity {

    private Button login, signin;
    private EditText user, pass;
    private CheckBox saveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

//        String[] userInfo = FileManager.loadUserData(Login.this);
//        if(loginValidation(userInfo[0], userInfo[1])) {
//            finish();
//            startMainApplication();
//        }

        login = (Button) findViewById(R.id.btn_login);
        signin = (Button) findViewById(R.id.btn_signin);
        saveData = (CheckBox) findViewById(R.id.save_user);

        user = (EditText) findViewById(R.id.usr);
        pass = (EditText) findViewById(R.id.pass);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String usr = user.getText().toString();
                String pas = pass.getText().toString();

                if (loginValidation(usr, pas)) {
                    startMainApplication();
                } else {
                    Snackbar.make(v, getResources().getText(R.string.wrong_data).toString(), Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignIn.class);
                startActivity(intent);
            }
        });
    }

    protected boolean loginValidation(String user, String pass) {
        String[] userInfo = FileManager.loadUserData(Login.this);

        if(userInfo != null && user.equals(userInfo[0]) && pass.equals(userInfo[1])) {
            User.user().setUsername(user);
            User.user().setEmail(userInfo[2]);
            return true;
        }

        return false;
    }

    private void startMainApplication() {
        Intent intent = new Intent(Login.this, MainActivity.class);
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

}
