package com.dangerducks.cookit;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dangerducks.cookit.DB.local.IngredientContract;
import com.dangerducks.cookit.DB.local.LocalDBOperations;

/**
 * Created by alex on 4/30/16.
 */
public class ShowIngredients extends AppCompatActivity {

    ListView ingredientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_ingredients);

        ingredientsList = (ListView) findViewById(R.id.ingredient_list);
        LocalDBOperations dbOperations = new LocalDBOperations(this);

        CursorAdapter adapter = new CursorAdapter(this, dbOperations.getIngredientCursor(), false) {
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
        ingredientsList.setAdapter(adapter);
    }
}
