package com.dangerducks.cookit.kitchen;

import java.io.*;
/**
 * Created by alex on 3/18/16.
 *
 */


public class Recipe implements Serializable {

    private static final long serialVersionUID =6529685098267757690L;

    private static int RID = -1;


    public String name;
    public int calories;
    public int portions;
    public int rating;
    public int dificulty;
    int duration;
    public boolean favourite;
    public Category category;
    public Preparation preparation;

    public Recipe() {
        preparation = new Preparation();
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

    public void setRID() {
        RID = RID++;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getPortions() {
        return portions;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDuration() {
        this.duration = 0;
        for(Step step: preparation.steps) {
            this.duration += step.getTime();
        }
    }

    public int getDuration() {
        return this.duration;
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

    public void addStep(Step step) {
        this.preparation.addStep(step);
    }

    public void removeStep(int index) {
        this.preparation.removeStep(index);
    }

}
