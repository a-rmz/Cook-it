package com.dangerducks.cookit.kitchen;

import java.io.*;
/**
 * Created by alex on 3/18/16.
 *
 */


public class Recipe implements Serializable {

    private static int RID;


    public String name;
    public int calories;
    public int portions;
    public int rating;
    public int dificulty;
    public boolean favourite;
    public Category category;
    public Preparation preparation;

    public Recipe() {

    }

    public Recipe(String name, Preparation preparation){

        this.preparation = preparation;
        this.name = name;
        calories = preparation.getCalories();
        portions = 4;
        rating = 0;
        dificulty = 0;
        favourite = false;
    }

    void changePortions(int portions) {
        this.portions = portions;
    }

    void rate(int rating) {
        do{
            if(isRatingValid(rating)) {
                this.rating = rating;
                break;
            } else {
                // Wrong rating message
            }
        } while (true);
    }

    boolean isRatingValid(int rating) {
        if((rating >= 0) && (rating <= 5)) return true;
        return false;
    }





}
