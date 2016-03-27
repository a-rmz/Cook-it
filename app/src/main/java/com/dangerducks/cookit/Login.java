package com.dangerducks.cookit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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

        if(loadUserData()) {
            finish();
            startMainApplication();
        }

        login = (Button) findViewById(R.id.btn_login);
        signin = (Button) findViewById(R.id.btn_signin);
        saveData = (CheckBox) findViewById(R.id.save_user);

        user = (EditText) findViewById(R.id.usr);
        pass = (EditText) findViewById(R.id.pass);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveData.isChecked()) {
                    if(loginValidation(user.getText().toString(), pass.getText().toString())) {
                        saveUserData(user.getText().toString(), pass.getText().toString());
                    }
                }
                if (loginValidation(user.getText().toString(), pass.getText().toString())) {
                    startMainApplication();
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
        if(user.length() > 0 && pass.length() > 0) {
            return true;
        }
        return false;
    }

    private boolean saveUserData(String user, String pass) {
        String buffer = user + "\n" + pass;

        File path = this.getApplicationContext().getFilesDir();
        File credentials = new File(path, "usr.crd");
        Log.v("dir: ", "cred dir: " + credentials.toString());

        try {
            FileOutputStream stream = new FileOutputStream(credentials);
            stream.write(buffer.getBytes());
            stream.close();
            Log.v("dir: ", "Saved data successfully");
        } catch (Exception e) {
            Log.v("saveData: ", "Failed to save data");
            return false;
        }

        return true;
    }

    public static boolean deleteUserData(Context context) {
        File path = context.getFilesDir();
        File credentials = new File(path, "usr.crd");

        return credentials.delete();
    }

    private boolean loadUserData() {
        String cred;
        File path = this.getApplicationContext().getFilesDir();
        File credentials = new File(path, "usr.crd");

        int length = (int) credentials.length();
        byte [] bytes = new byte[length];

        try {
            FileInputStream inputStream = new FileInputStream(credentials);
            inputStream.read(bytes);
            inputStream.close();

            cred = new String(bytes);
        } catch (Exception e) {
            Log.v("loadData: ", "Failed to load data");
            return false;
        }

        String user = cred.substring(0, cred.indexOf("\n"));
        String pass = cred.substring(cred.indexOf("\n") + 1, cred.length());

        return loginValidation(user, pass);
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
