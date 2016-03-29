package com.dangerducks.cookit.kitchen;


import android.media.Image;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by alex on 3/18/16.
 */
public class Step implements Serializable {

    String description;
    int time;
    transient Image image;
    Unit unit;
    Vector<Ingredient> ingredients;

    public Step() {
        ingredients = new Vector<>();
    }

    public Step(String description){
        super();
        this.description = description;
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

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void removeIngredient(int index) {
        ingredients.remove(index);
    }
}
