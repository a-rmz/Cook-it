package com.dangerducks.cookit;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.Button;

/**
 * Created by alex on 4/29/16.
 */
public class AddIngredientDialog extends AppCompatDialog {

    Context context;
    Button done;

    public AddIngredientDialog(Context context) {
        super(context);
        this.context = context;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ingredient);

        done = (Button) findViewById(R.id.add_ingredient_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
