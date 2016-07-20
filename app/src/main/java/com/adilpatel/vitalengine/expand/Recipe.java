package com.adilpatel.vitalengine.expand;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

/**
 * Created by Adil on 7/20/16.
 */
public class Recipe implements ParentListItem {

    private String mName;
    private List<Ingredient> mIngredients;

    public Recipe(String name, List<Ingredient> ingredients) {
        mName = name;
        mIngredients = ingredients;
    }

    public String getName() {
        return mName;
    }

    @Override
    public List<?> getChildItemList() {
        return mIngredients;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}