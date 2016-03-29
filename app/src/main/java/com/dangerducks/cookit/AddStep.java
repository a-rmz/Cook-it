package com.dangerducks.cookit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dangerducks.cookit.kitchen.Ingredient;
import com.dangerducks.cookit.kitchen.Recipe;
import com.dangerducks.cookit.kitchen.Step;

/**
 * Created by alex on 3/27/16.
 */
public class AddStep extends AppCompatDialog {

    Activity activity;
    Spinner ingredients;
    String stepName;
    Step step;
    Recipe recipe;
    Button done;
    int ingredientsAdded = 0;

    public AddStep(Activity activity, String stepName, Recipe recipe) {
        super(activity);
        this.activity = activity;
        this.stepName = stepName;
        step = new Step();
        this.recipe = recipe;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_step);

        TextView stepDescription = (TextView) findViewById(R.id.step_description);
        stepDescription.setText(stepName);

        done = (Button) findViewById(R.id.finish_add_ingredient);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String duration = ((EditText) findViewById(R.id.add_duration)).getText().toString();
                if(!duration.isEmpty() && ingredientsAdded > 0) {
                    step.setTime(Integer.parseInt(duration));
                    recipe.addStep(step);
                    dismiss();
                } else {
                    Snackbar.make(v, activity.getResources().getString(R.string.empty_time), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        ingredients = (Spinner) findViewById(R.id.ingredient_base);
        setupSpinner();

        setupIngredientAdder();
    }

    private void setupSpinner() {
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity.getApplicationContext(), R.array.ingredients, R.layout.spinner_element);
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
                    final View view = inflater.inflate(R.layout.add_step_ingredient, null);

                    TextView textOut = (TextView) view.findViewById(R.id.ingredient);

                    String ingredientName = ingredients.getSelectedItem().toString();
                    Ingredient ingredient = new Ingredient(ingredientName);
                    textOut.setText(ingredientName);

                    Button remove = (Button) view.findViewById(R.id.remove_ingredient_btn);
                    remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((LinearLayout) view.getParent()).removeView(view);
                            step.removeIngredient(--ingredientsAdded);
                        }
                    });

                    step.addIngredient(ingredient);
                    container.addView(view);
                    ingredientsAdded++;

                }


            }
        });


    }


}
