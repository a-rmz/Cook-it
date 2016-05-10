package com.dangerducks.cookit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.dangerducks.cookit.DB.DBFunct;
import com.dangerducks.cookit.kitchen.Recipe;
import com.dangerducks.cookit.utils.FileManager;

public class Login extends AppCompatActivity {

    private Button login, signin;
    private EditText user, pass;
    private CheckBox saveData;
    DBFunct db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        db = new DBFunct();

        if(verifyLogin()) {
            setUserVals();
            startMainApplication();
            finish();
        }

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
                String mail =  db.getMail(usr);


                if (loginValidation(usr, pas)) {
                    if(saveData.isChecked()) {
                        SharedPreferences sharedPreferences = getSharedPreferences(
                                getString(R.string.preferences_key),
                                MODE_PRIVATE
                        );
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("USER", usr);
                        editor.putString("MAIL", mail);
                        editor.putString("PASS", pas);
                        editor.apply();
                    }

                    setUserVals(usr, mail);
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
        return db.Login(user, pass);
    }

    private void setUserVals(String uname, String email) {
        User.user().setUsername(uname);
        User.user().setEmail(email);
    }

    private void setUserVals() {
        SharedPreferences sharedPreferences = (
                this.getSharedPreferences(
                        getString(R.string.preferences_key),
                        Context.MODE_PRIVATE
                )
        );
        String uname = sharedPreferences.getString("USER", null);
        String email = sharedPreferences.getString("MAIL", null);
        setUserVals(uname, email);
        sharedPreferences = null;
    }

    private void startMainApplication() {
        RecipeLoader RL = new RecipeLoader();
        RL.execute();
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

    /**
     * Validates if the user selected the Save login data checkbox
     */
    private boolean verifyLogin() {
        SharedPreferences sharedPreferences = (
            this.getSharedPreferences(
                    getString(R.string.preferences_key),
                    Context.MODE_PRIVATE
                )
            );
        String uname = sharedPreferences.getString("USER", null);
        String pass = sharedPreferences.getString("PASS", null);
        sharedPreferences = null;
        return (!(uname == null && pass == null)) && db.Login(uname, pass);
    }




    private class RecipeLoader extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            User.user().recipesSaved.removeAllElements();
            User.user().recipesSaved.addAll(FileManager.getRecipes(getFilesDir().getPath()));

            for(Recipe r : User.user().recipesSaved) {
                if(r.isFavourite()) User.user().addFavouriteRecipe(r);
            }
            return null;
        }

    }

}
