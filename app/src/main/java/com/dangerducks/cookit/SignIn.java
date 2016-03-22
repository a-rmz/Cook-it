package com.dangerducks.cookit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by alex on 3/21/16.
 */
public class SignIn extends AppCompatActivity {

    Button btn;
    EditText usr, pass, mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_layout);

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

                validate(username, password, email);
            }
        });
    }

    private void validate(String username, String password, String email) {

    }

    private boolean validateUsername(String user) {
        return true;
    }

    private boolean validatePassword(String pass) {
        if(!pass.isEmpty() && pass.length() <= 25) return true;
        return false;
    }

}
