package com.dangerducks.cookit.kitchen;

<<<<<<< HEAD
import java.util.*;
=======
import java.util.Vector;
>>>>>>> 7d110677927d02f3fcf87c3d4b368e23fe8e9b4b

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

<<<<<<< HEAD
    void deleteStep(Step step){
        steps.remove(step);
=======
    void removeStep(int index) {
        steps.remove(index);
>>>>>>> 7d110677927d02f3fcf87c3d4b368e23fe8e9b4b
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
