package com.dangerducks.cookit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by alex on 3/27/16.
 */
public class AddStep extends AppCompatDialog {

    Activity activity;
    Spinner ingredients;
    String step;
    Button done;
    int ingredientsAdded = 0;

    public AddStep(Activity activity, String step) {
        super(activity);
        this.activity = activity;
        this.step = step;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_step);

        TextView stepDescription = (TextView) findViewById(R.id.step_description);
        stepDescription.setText(step);

        done = (Button) findViewById(R.id.finish_add_ingredient);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ingredients = (Spinner) findViewById(R.id.ingredient_base);
        setupSpinner();

        setupIngredientAdder();
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity.getApplicationContext(), R.array.ingredients, R.layout.spinner_element);
        ingredients.setAdapter(adapter);
        ingredients.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setupIngredientAdder() {
        Button add = (Button) findViewById(R.id.add_ingredient_btn);
        final LinearLayout container = (LinearLayout) findViewById(R.id.ingredient_container);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ingredients.getSelectedItemId() != 0) {
                    LayoutInflater inflater = (LayoutInflater) activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View ingredient = inflater.inflate(R.layout.add_step_ingredient, null);

                    TextView textOut = (TextView) ingredient.findViewById(R.id.ingredient);

                    String ing = ingredients.getSelectedItem().toString();
                    textOut.setText(ing);

                    Button remove = (Button) ingredient.findViewById(R.id.remove_ingredient_btn);
                    remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((LinearLayout) ingredient.getParent()).removeView(ingredient);
                        }
                    });

                    container.addView(ingredient);
                    ingredientsAdded++;

                }


            }
        });


    }


}
