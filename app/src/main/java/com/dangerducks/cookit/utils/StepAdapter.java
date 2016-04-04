package com.dangerducks.cookit.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dangerducks.cookit.R;
import com.dangerducks.cookit.kitchen.Ingredient;
import com.dangerducks.cookit.kitchen.Step;

import java.util.Vector;

/**
 * Created by alex on 4/2/16.
 */
public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {

    Vector<Step> steps;
    IngredientAdapter ingredientAdapter;

    public static class StepHolder extends RecyclerView.ViewHolder {
        TextView stepNumber, stepDescription, stepDuration;
        RecyclerView recyclerView;

        public StepHolder(View itemView) {
            super(itemView);
            stepNumber = (TextView) itemView.findViewById(R.id.show_step_number);
            stepDuration = (TextView) itemView.findViewById(R.id.show_step_duration);
            stepDescription = (TextView) itemView.findViewById(R.id.show_step_description);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.show_step_ingredient_container);
        }
    }

    public StepAdapter(Vector<Step> steps) {
        super();
        this.steps = steps;
    }

    protected void setIngredientAdapter(RecyclerView recyclerView, Step step) {
        ingredientAdapter = new IngredientAdapter(step.getIngredients());
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(ingredientAdapter);
    }


    @Override
    public int getItemCount() {
        return steps.size();
    }

    @Override
    public StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step, parent, false);
        return new StepHolder(view);
    }

    @Override
    public void onBindViewHolder(StepHolder holder, int position) {
        setIngredientAdapter(holder.recyclerView, steps.elementAt(position));
        holder.stepDescription.setText(steps.elementAt(position).getDescription());
        holder.stepDuration.setText(steps.elementAt(position).getTime() + " minutes");
        holder.stepNumber.setText("Step " + (position+1));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
