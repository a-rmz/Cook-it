package com.dangerducks.cookit.kitchen;

import java.io.*;
/**
 * Created by alex on 3/18/16.
 *
 */


public class Recipe implements Serializable {

    private static int RID = -1;


    public String name;
    public int calories;
    public int portions;
    public int rating;
    public int dificulty;
    public boolean favourite;
    public Category category;
    public Preparation preparation;

    public Recipe() {
        preparation = new Preparation();
    }

    public void setRID() {
        RID = RID++;
    }

<<<<<<< HEAD
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
=======
    public void setPortions(int portions) {
>>>>>>> 7d110677927d02f3fcf87c3d4b368e23fe8e9b4b
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

<<<<<<< HEAD
=======
    public boolean saveRecipe(){
>>>>>>> 7d110677927d02f3fcf87c3d4b368e23fe8e9b4b



<<<<<<< HEAD
=======
        return false;

    }

    public Recipe loadRecipe(int ID){
        String fileName = ID + ".obj";
        Recipe aux = new Recipe();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            aux = (Recipe)in.readObject();
            in.close();

        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return aux;
    }
>>>>>>> 7d110677927d02f3fcf87c3d4b368e23fe8e9b4b

    public void addStep(Step step) {
        this.preparation.addStep(step);
    }

    public void removeStep(int index) {
        this.preparation.removeStep(index);
    }

}
