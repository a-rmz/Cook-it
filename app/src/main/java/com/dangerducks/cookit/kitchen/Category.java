package com.dangerducks.cookit.kitchen;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by alex on 3/18/16.
 */
public class Category implements Serializable {

    public static int CID;

    String name;
    String description;
    public static int recipeQuantity;
    Vector<Recipe> recipes;


    public Category(){

    }

    public Category(String name) {
        this.name = name;
    }

    public void sortRecipes() {
        // Sort magic
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String desc){
        this.description = desc;
    }

    public void addRecipe(Recipe recipe){
        this.recipes.add(recipe);
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }


}
