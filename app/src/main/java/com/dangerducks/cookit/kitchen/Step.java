package com.dangerducks.cookit.kitchen;


import android.media.Image;

import java.util.Vector;

/**
 * Created by alex on 3/18/16.
 */
public class Step {

    String description;
    int time;
<<<<<<< HEAD
    String instruction;
=======
    Image image;
    Unit unit;
    Vector<Ingredient> ingredients;

    public Step() {
        ingredients = new Vector<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
>>>>>>> 7d110677927d02f3fcf87c3d4b368e23fe8e9b4b

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

<<<<<<< HEAD
    public Step(String instruction){
        this.instruction = instruction;
    }

=======
    public void removeIngredient(int index) {
        ingredients.remove(index);
    }
>>>>>>> 7d110677927d02f3fcf87c3d4b368e23fe8e9b4b
}
