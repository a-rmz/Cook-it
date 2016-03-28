package com.dangerducks.cookit.kitchen;

import java.io.*;
/**
 * Created by alex on 3/18/16.
 */
public class Recipe implements Serializable {

    private static int RID = -1;

    String name;
    int calories;
    int portions;
    int rating;
    int dificulty;
    boolean favourite;
    Category category;
    Preparation preparation;

    public Recipe() {
        preparation = new Preparation();
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

    public boolean saveRecipe(){

        String fileName = RID + ".obj";
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(this);
            out.close();
            return true;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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

    public void addStep(Step step) {
        this.preparation.addStep(step);
    }

    public void removeStep(int index) {
        this.preparation.removeStep(index);
    }

}
