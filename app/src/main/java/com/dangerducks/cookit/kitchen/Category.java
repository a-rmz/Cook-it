package com.dangerducks.cookit.kitchen;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by alex on 3/18/16.
 */
public class Category implements Serializable {

    public static int CID;

    String name;
    String description;
    public static int recipeQuantity;
    Vector<Recipe> recipes;

    public Category(String name) {
        this.name = name;
    }

    void sortRecipes() {
        // Sort magic
    }
}
