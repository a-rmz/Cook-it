package com.dangerducks.cookit.kitchen;

import java.util.Vector;

/**
 * Created by alex on 3/18/16.
 */
public class Category {

    public static int CID;

    String name;
    String description;
    public static int recipeQuantity;
    Vector<Recipe> recipes;

    public Category() {

    }

    void sortRecipes() {
        // Sort magic
    }
}
