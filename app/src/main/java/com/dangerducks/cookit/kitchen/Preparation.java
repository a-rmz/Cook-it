package com.dangerducks.cookit.kitchen;

import java.io.Serializable;
import java.util.Vector;
/**
 * Created by alex on 3/18/16.
 */
public class Preparation implements Serializable {

    static final long serialVersionUID =-1097412748889190380L;

    Vector<Step> steps;
    //Vector<Ingredient> ingredients;
    int actualStep;
    int preparationTime;

    public Preparation() {
        actualStep = 0;
        steps = new Vector<>();
    }

    public Step showStep() {
        return steps.elementAt(actualStep);
    }

    public Step nextStep() {
        if(++actualStep < steps.size()) return steps.elementAt(actualStep);
        return null;
    }

    public Step previousStep() {
        if(--actualStep >= 0) return steps.elementAt(actualStep);
        return null;
    }

    void changeUnits() {

    }

    public void addStep(Step step) {
        steps.add(step);
    }

    public void removeStep(int index) {
        steps.remove(index);
    }

//    public void addIngredient(Ingredient ingredient){
//        ingredients.add(ingredient);
//    }
//
//    public int getCalories(){
//        int totalCalories = 0;
//
//        for (Ingredient i : ingredients) {
//            totalCalories += i.calories;
//        }
//
//        return totalCalories;
//    }

    public Vector<Step> getSteps() {
        return this.steps;
    }

    public int getPreparationTime() {
        int time = 0;
        for (Step s : steps) {
            time += s.getTime();
        }
        return time;
    }
}
