package com.dangerducks.cookit.DB.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dangerducks.cookit.kitchen.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 4/29/16.
 */
public class LocalDBOperations {

    private LocalDBHandler localDBHandler;

    public LocalDBOperations(Context context) {
        localDBHandler = new LocalDBHandler(context);
    }


    public long addIngredient(Ingredient ingredient) {
        SQLiteDatabase db = localDBHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(IngredientContract.IngredientEntry.KEY_NAME,          ingredient.getName());
        values.put(IngredientContract.IngredientEntry.KEY_CALORIES,      ingredient.getCalories());
        values.put(IngredientContract.IngredientEntry.KEY_DISPONIBILITY, ingredient.getDisponibility());

        return db.insert(IngredientContract.IngredientEntry.TABLE_INGREDIENTS, null, values);
    }

    public List<Ingredient> getIngredients() {
        List<Ingredient> ingredientList = null;
        SQLiteDatabase db = localDBHandler.getWritableDatabase();

        String[] PROJECTION = {
                IngredientContract.IngredientEntry._ID,
                IngredientContract.IngredientEntry.KEY_NAME,
                IngredientContract.IngredientEntry.KEY_CALORIES,
                IngredientContract.IngredientEntry.KEY_DISPONIBILITY
        };

        String SORT_ORDER = IngredientContract.IngredientEntry.TABLE_INGREDIENTS + " ASC";

        Cursor c = db.query(
                IngredientContract.IngredientEntry.TABLE_INGREDIENTS,     // The table to query
                PROJECTION,                                               // The columns to return
                null,                                                     // The columns for the WHERE clause
                null,                                                     // The values for the WHERE clause
                null,                                                     // don't group the rows
                null,                                                     // don't filter by row groups
                SORT_ORDER
        );


        if (c != null && c.moveToFirst()) {
            Ingredient ingredient;
            ingredientList = new ArrayList<>();
            do {
                ingredient = new Ingredient();
                ingredient.setIID(c.getInt(0));
                ingredient.setName(c.getString(1));
                ingredient.setCalories(c.getInt(2));
                ingredient.setDisponibility(c.getInt(3));

                ingredientList.add(ingredient);
            } while (c.moveToNext());
        }

        return ingredientList;
    }

}
