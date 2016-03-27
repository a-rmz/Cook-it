package com.dangerducks.cookit;

import android.graphics.Picture;

import com.dangerducks.cookit.kitchen.Ingredient;
import com.dangerducks.cookit.kitchen.Recipe;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by alex on 3/18/16.
 */
public class User {

    public static int UID;

    String username;
    String email;
    String name;
    String lastName;
    Picture profilePicture;
    Vector<Ingredient> dislikes;
    ArrayList<Recipe> favourites;

    private static User instance = new User();

    private User() { }

    public static User user() {
        return instance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Adds a recipe to the favourites list. If the recipe was added successfully (not favourite already), returns true; otherwise returns false.
     * @param recipe
     * @return
     */
    boolean addFavouriteRecipe(Recipe recipe) {
        if(!isRecipeFavourite(recipe)) {
            favourites.add(recipe);
            return true;
        }
        return false;
    }

    /**
     * Removes a recipe from the favourites list. If the recipe was removed successfully, returns true; otherwise returns false.
     * @param recipe
     * @return
     */
    boolean removeFavouriteRecipe(Recipe recipe) {
        if(isRecipeFavourite(recipe)) {
            favourites.remove(recipe);
            return true;
        }
        return false;
    }

    boolean isRecipeFavourite(Recipe recipe) {
        if(favourites.contains(recipe)) return true;
        return false;
    }

    boolean dislikesIngredient(Ingredient ingredient) {
        if(dislikes.contains(ingredient)) return true;
        return false;
    }


}
