package com.dangerducks.cookit.utils;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dangerducks.cookit.R;
import com.dangerducks.cookit.kitchen.Ingredient;

import java.util.Vector;

/**
 * Created by alex on 4/4/16.
 */
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder> {

    Vector<Ingredient> ingredients;

    public static class IngredientHolder extends RecyclerView.ViewHolder {
        TextView ingredient;

        public IngredientHolder(View view) {
            super(view);
            ingredient = (TextView) view.findViewById(R.id.ingredient_name);
        }

    }

    public IngredientAdapter(Vector<Ingredient> ingredients) {
        super();
        this.ingredients = ingredients;
    }

    @Override
    public IngredientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient, parent, false);
        return new IngredientHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientHolder holder, int position) {
        holder.ingredient.setText(ingredients.elementAt(position).getName());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
