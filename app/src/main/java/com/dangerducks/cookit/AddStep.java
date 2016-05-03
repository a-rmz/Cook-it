package com.dangerducks.cookit;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dangerducks.cookit.DB.local.IngredientContract;
import com.dangerducks.cookit.DB.local.LocalDBOperations;
import com.dangerducks.cookit.kitchen.Ingredient;
import com.dangerducks.cookit.kitchen.Recipe;
import com.dangerducks.cookit.kitchen.Step;

/**
 * Created by alex on 3/27/16.
 */
public class AddStep extends AppCompatDialog {

    Activity activity;
    Spinner ingredientsSpinner;
    String stepName;
    Step step;
    Recipe recipe;
    Button done;
    Button add;
    int ingredientsAdded = 0;

    public AddStep(Activity activity, String stepName, Recipe recipe) {
        super(activity);
        this.activity = activity;
        this.stepName = stepName;
        step = new Step();
        step.setDescription(stepName);
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
                if(!duration.isEmpty()) {
                    step.setTime(Integer.parseInt(duration));
                    recipe.preparation.addStep(step);
                    dismiss();
                } else {
                    Toast t = Toast.makeText(v.getContext(),  activity.getResources().getString(R.string.empty_time), Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 30);
                    t.show();
                }
            }
        });

        ingredientsSpinner = (Spinner) findViewById(R.id.ingredient_base);
        setupSpinner();

        add = (Button) findViewById(R.id.add_ingredient_btn);
        setupIngredientAdder();
    }

    private void setupSpinner() {
        LocalDBOperations dbOperations = new LocalDBOperations(getContext());
        final CursorAdapter adapter = new CursorAdapter(getContext(), dbOperations.getIngredientCursor(), false) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.ingredient, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView text = (TextView) view.findViewById(R.id.ingredient_name);
                String name = cursor.getString(cursor.getColumnIndex(IngredientContract.IngredientEntry.KEY_NAME));
                int cals = cursor.getInt(cursor.getColumnIndex(IngredientContract.IngredientEntry.KEY_CALORIES));
                text.setText(name);
                text = (TextView) view.findViewById(R.id.ingredient_cals);
                text.setText(cals + " calories");
            }
        };
        ingredientsSpinner.setAdapter(adapter);
    }


    private void setupIngredientAdder() {

        final LinearLayout container = (LinearLayout) findViewById(R.id.ingredient_container);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ingredientsSpinner.getSelectedItemId() != 0) {
                    LayoutInflater inflater = (LayoutInflater) activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View view = inflater.inflate(R.layout.add_step_ingredient, null);

                    TextView textOut = (TextView) view.findViewById(R.id.ingredient);

                    Ingredient ingredient = Ingredient.createIngredientFromCursor((Cursor) ingredientsSpinner.getSelectedItem());

                    textOut.setText(ingredient.getName());

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
