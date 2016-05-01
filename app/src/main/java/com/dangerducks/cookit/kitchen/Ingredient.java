package com.dangerducks.cookit.kitchen;

import java.io.Serializable;

/**
 * Created by alex on 3/18/16.
 */
public class Ingredient implements Serializable{

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

}
