package com.dangerducks.cookit.utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dangerducks.cookit.R;
import com.dangerducks.cookit.kitchen.Recipe;

import java.util.Vector;

/**
 * Created by alex on 4/1/16.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    Vector<Recipe> recipes;


    public static class RecipeHolder extends RecyclerView.ViewHolder {
        CardView holder;
        TextView title, portions;

        public RecipeHolder(View view)  {
            super(view);
            holder = (CardView) view.findViewById(R.id.recipe_card_layout);
            title = (TextView) view.findViewById(R.id.card_header);
            portions = (TextView) view.findViewById(R.id.card_show_portions);
        }
    }


    public RecipeAdapter(Vector<Recipe> recipes) {
        super();
        this.recipes = recipes;
    }

    public void remove(int position) {
        recipes.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        return new RecipeHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {
        holder.title.setText(recipes.get(position).getName());
        holder.portions.setText(recipes.get(position).getPortions() + " portions");
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
