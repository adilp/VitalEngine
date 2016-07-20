package com.adilpatel.vitalengine.expand;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adilpatel.vitalengine.R;
import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

/**
 * Created by Adil on 7/20/16.
 */
public class RecipeAdapter extends ExpandableRecyclerAdapter<RecipeViewHolder, IngredientViewHolder> {

    private LayoutInflater mInflator;

    public RecipeAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        mInflator = LayoutInflater.from(context);
    }

    @Override
    public RecipeViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View recipeView = mInflator.inflate(R.layout.list_item_create_team, parentViewGroup, false);
        return new RecipeViewHolder(recipeView);
    }

    @Override
    public IngredientViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View ingredientView = mInflator.inflate(R.layout.list_item_create_team_child, childViewGroup, false);
        return new IngredientViewHolder(ingredientView);
    }

    @Override
    public void onBindParentViewHolder(RecipeViewHolder recipeViewHolder, int position, ParentListItem parentListItem) {
        Recipe recipe = (Recipe) parentListItem;
        recipeViewHolder.bind(recipe);
    }

    @Override
    public void onBindChildViewHolder(IngredientViewHolder ingredientViewHolder, int position, Object childListItem) {
        Ingredient ingredient = (Ingredient) childListItem;
        ingredientViewHolder.bind(ingredient);
    }
}
