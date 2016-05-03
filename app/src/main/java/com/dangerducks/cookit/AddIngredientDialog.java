package com.dangerducks.cookit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dangerducks.cookit.DB.local.LocalDBOperations;
import com.dangerducks.cookit.kitchen.Ingredient;

/**
 * Created by alex on 4/29/16.
 */
public class AddIngredientDialog extends AppCompatDialog {

    Context context;
    Button done, cancel;
    EditText name, calories, available;

    public AddIngredientDialog(Context context) {
        super(context);
        this.context = context;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ingredient);

        name = (EditText) findViewById(R.id.ingredient_name);
        calories = (EditText) findViewById(R.id.ingredient_calories);
        available = (EditText) findViewById(R.id.ingredient_availability);

        done = (Button) findViewById(R.id.add_ingredient_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    Ingredient ingredient = new Ingredient(
                            name.getText().toString(),
                            Integer.parseInt(calories.getText().toString()),
                            Integer.parseInt(available.getText().toString())
                    );
                    LocalDBOperations localDBOperations = new LocalDBOperations(v.getContext());
                    localDBOperations.addIngredient(ingredient);
                    dismiss();
                }
            }
        });

        cancel = (Button) findViewById(R.id.add_ingredient_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private boolean validate() {
        if( name.getText().toString().length() > 0 &&
            Integer.parseInt(calories.getText().toString()) > 0 &&
            Integer.parseInt(available.getText().toString()) > 0 )
            return true;
        return false;
    }

}
