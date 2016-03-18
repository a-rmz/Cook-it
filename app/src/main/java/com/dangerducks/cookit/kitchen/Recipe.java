package com.dangerducks.cookit.kitchen;

/**
 * Created by alex on 3/18/16.
 */
public class Recipe {

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

}
