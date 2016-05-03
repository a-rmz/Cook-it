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

        Cursor c = getIngredientCursor();


        if (c != null && c.moveToFirst()) {
            ingredientList = new ArrayList<>();
            do {
                ingredientList.add(Ingredient.createIngredientFromCursor(c));
            } while (c.moveToNext());
        }

        return ingredientList;
    }

    public Cursor getSelectedIngredient(int ID) {
        SQLiteDatabase db = localDBHandler.getWritableDatabase();

        String[] PROJECTION = {
                IngredientContract.IngredientEntry._ID,
                IngredientContract.IngredientEntry.KEY_NAME,
                IngredientContract.IngredientEntry.KEY_CALORIES,
                IngredientContract.IngredientEntry.KEY_DISPONIBILITY
        };

        String WHERE = IngredientContract.IngredientEntry._ID + "=?";
        String ARGS[] = {ID + ""};

        String SORT_ORDER = IngredientContract.IngredientEntry.KEY_NAME + " ASC";

        Cursor c = db.query(
                IngredientContract.IngredientEntry.TABLE_INGREDIENTS,     // The table to query
                PROJECTION,                                               // The columns to return
                WHERE,                                                    // The columns for the WHERE clause
                ARGS,                                                     // The values for the WHERE clause
                null,                                                     // don't group the rows
                null,                                                     // don't filter by row groups
                SORT_ORDER
        );

        return c;
    }

    public Cursor getIngredientCursor() {
        SQLiteDatabase db = localDBHandler.getWritableDatabase();

        String[] PROJECTION = {
                IngredientContract.IngredientEntry._ID,
                IngredientContract.IngredientEntry.KEY_NAME,
                IngredientContract.IngredientEntry.KEY_CALORIES,
                IngredientContract.IngredientEntry.KEY_DISPONIBILITY
        };

        String SORT_ORDER = IngredientContract.IngredientEntry.KEY_NAME + " ASC";

        Cursor c = db.query(
                IngredientContract.IngredientEntry.TABLE_INGREDIENTS,     // The table to query
                PROJECTION,                                               // The columns to return
                null,                                                     // The columns for the WHERE clause
                null,                                                     // The values for the WHERE clause
                null,                                                     // don't group the rows
                null,                                                     // don't filter by row groups
                SORT_ORDER
        );

        return c;
    }

}
