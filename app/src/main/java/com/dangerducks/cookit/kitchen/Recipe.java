package com.dangerducks.cookit.kitchen;

import java.io.*;
/**
 * Created by alex on 3/18/16.
 */
public class Recipe implements Serializable {

    private static int RID;

    String name;
    int calories;
    int portions;
    int rating;
    int dificulty;
    boolean favourite;
    Category category;
    Preparation preparation;

    public Recipe() {

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

    boolean saveRecipe(){

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

    Recipe loadRecipe(int ID){
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

}
