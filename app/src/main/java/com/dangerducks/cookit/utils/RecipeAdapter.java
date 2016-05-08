package com.dangerducks.cookit.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dangerducks.cookit.R;
import com.dangerducks.cookit.ShowRecipe;
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
        ImageView fav;
        Recipe recipe;

        public RecipeHolder(View view)  {
            super(view);
            holder = (CardView) view.findViewById(R.id.recipe_card_layout);
            title = (TextView) view.findViewById(R.id.card_header);
            portions = (TextView) view.findViewById(R.id.card_show_portions);
            fav = (ImageView) view.findViewById(R.id.fav_star);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ShowRecipe.class);
                    intent.putExtra("recipe", recipe);
                    ((Activity) v.getContext()).startActivityForResult(intent, 1, null);
                }
            });
        }

        public void setRecipe(Recipe recipe) {
            this.recipe = recipe;
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

    public void clear() {
        recipes.clear();
        notifyDataSetChanged();
    }

    public void update(Vector<Recipe> recipes) {
        this.recipes.removeAllElements();
        notifyItemRangeRemoved(0, recipes.size() + 1);
        this.recipes = recipes;
        notifyItemRangeInserted(0, recipes.size());
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
        holder.setRecipe(recipes.elementAt(position));
        holder.title.setText(recipes.elementAt(position).getName());
        holder.portions.setText(recipes.elementAt(position).getPortions() + " portions");
        holder.fav.setVisibility((recipes.elementAt(position).isFavourite()) ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
