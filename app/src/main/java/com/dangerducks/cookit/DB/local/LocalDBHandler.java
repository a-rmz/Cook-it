package com.dangerducks.cookit.DB.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dangerducks.cookit.kitchen.Ingredient;

/**
 * Created by alex on 4/29/16.
 */
public class LocalDBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "local.db";

    String CREATE_INGREDIENTS_TABLE =
            "CREATE TABLE "     + IngredientContract.IngredientEntry.TABLE_INGREDIENTS + "(" +
                    IngredientContract.IngredientEntry._ID                 + " INTEGER PRIMARY KEY NOT NULL," +
                    IngredientContract.IngredientEntry.KEY_NAME            + " TEXT NOT NULL," +
                    IngredientContract.IngredientEntry.KEY_CALORIES        + " INTEGER NOT NULL," +
                    IngredientContract.IngredientEntry.KEY_DISPONIBILITY   + " INTEGER)";



    public LocalDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_INGREDIENTS_TABLE);
    }



    // Called when there is a database version mismatch
    // meaning that the version of the database on disk
    // needs to be upgraded to the current version.
        @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    // Log the version upgrade.
            Log.w("TaskDBAdapter", "Upgrading from version " +
                    oldVersion + " to " + newVersion + ", which will destroy all old data");
    // Upgrade the existing database to conform to the new
    // version. Multiple previous versions can be handled
    // by comparing oldVersion and newVersion values.
    // The simplest case is to drop the old table and
    // create a new one.
            db.execSQL("DROP TABLE IF IT EXISTS " + IngredientContract.IngredientEntry.TABLE_INGREDIENTS);

    // Create a new one.
        onCreate(db);
    }

}