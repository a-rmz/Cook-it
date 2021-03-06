package com.dangerducks.cookit.kitchen;

import java.io.Serializable;
import java.util.Vector;
/**
 * Created by alex on 3/18/16.
 */
public class Preparation implements Serializable {

    Vector<Step> steps;
    Vector<Ingredient> ingredients;
    int actualStep;
    int preparationTime;

    public Preparation() {
        actualStep = 0;
        steps = new Vector<>();
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

    void addStep(Step step) {
        steps.add(step);
    }

    void removeStep(int index) {
        steps.remove(index);
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
