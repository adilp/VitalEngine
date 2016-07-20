package com.adilpatel.vitalengine.expand;

import android.view.View;
import android.widget.TextView;

import com.adilpatel.vitalengine.R;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

/**
 * Created by Adil on 7/20/16.
 */


    public class IngredientViewHolder extends ChildViewHolder {

        private TextView mIngredientTextView;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            mIngredientTextView = (TextView) itemView.findViewById(R.id.teamNameChild);
        }

        public void bind(Ingredient ingredient) {
            mIngredientTextView.setText(ingredient.getName());
        }
    }

