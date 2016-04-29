package com.dangerducks.cookit.DB.local;

import android.provider.BaseColumns;

/**
 * Created by alex on 4/29/16.
 */
public class IngredientContract {

    public static abstract class IngredientEntry implements BaseColumns {
        // Table names
        public static final String TABLE_INGREDIENTS = "INGREDIENTS";

        // TABLE_INGREDIENTS Columns
        public static final String KEY_NAME          = "name";
        public static final String KEY_CALORIES      = "calories";
        public static final String KEY_DISPONIBILITY = "disp";
    }

}
