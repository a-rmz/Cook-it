package com.dangerducks.cookit.kitchen;

import android.database.Cursor;

import java.io.Serializable;

/**
 * Created by alex on 3/18/16.
 */
public class Ingredient implements Serializable {

    private static final long serialVersionUID =-8402719210047104980L;

    public static int IID;

    String name;
    int calories;
    int disponibility;

    public Ingredient(String name, int calories, int disponibility) {
        this.name = name;
        this.calories = calories;
        this.disponibility = disponibility;
    }

    public Ingredient() {

    }

    public static void setIID(int IID) {
        Ingredient.IID = IID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCalories() {
        return calories;
    }

    public void setDisponibility(int disponibility) {
        this.disponibility = disponibility;
    }

    public int getDisponibility() {
        return this.disponibility;
    }

    public static Ingredient createIngredientFromCursor(Cursor c) {
        Ingredient ingredient = new Ingredient();
        ingredient.setIID(c.getInt(0));
        ingredient.setName(c.getString(1));
        ingredient.setCalories(c.getInt(2));
        ingredient.setDisponibility(c.getInt(3));
        return ingredient;
    }
}
