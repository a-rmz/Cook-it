package com.dangerducks.cookit.kitchen;

import java.util.Vector;

/**
 * Created by alex on 3/18/16.
 */
public class Preparation {

    Vector<Step> steps;
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

    void addStep(Step step) {
        steps.add(step);
    }

    void removeStep(int index) {
        steps.remove(index);
    }

}
