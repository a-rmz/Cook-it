package com.dangerducks.cookit.kitchen;

import java.util.*;

/**
 * Created by alex on 3/18/16.
 */
public class Preparation {

    Vector<Step> steps;
    Vector<Ingredient> ingredients;
    int actualStep;
    int preparationTime;

    public Preparation() {
        actualStep = 0;
    }

    Step showStep() {
        return steps.elementAt(actualStep);
    }

    Step nextStep() {
        if(++actualStep < steps.size()) return steps.elementAt(actualStep);
        return null;
    }

    Step previousStep() {
        if(--actualStep >= 0) return steps.elementAt(actualStep);
        return null;
    }

    void changeUnits() {

    }

    void addStep(Step step){
        steps.add(step);
    }

    void deleteStep(Step step){
        steps.remove(step);
    }

    void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
    }

    int getCalories(){
        int totalCalories = 0;

        for (Ingredient i : ingredients) {
            totalCalories += i.calories;
        }
        
        return totalCalories;
    }

}
