package com.dangerducks.cookit.kitchen;

/**
 * Created by alex on 3/18/16.
 */
public class Ingredient {

    public static int IID;

    String name;
    int calories;
    int disponibility;

    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
