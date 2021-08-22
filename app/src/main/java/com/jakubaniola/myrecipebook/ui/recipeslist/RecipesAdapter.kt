package com.jakubaniola.myrecipebook.ui.recipeslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.jakubaniola.myrecipebook.R
import com.jakubaniola.myrecipebook.database.databaseobjects.RecipeDO

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    private var recipes: List<RecipeDO> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_recipe,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
    }

    fun setTodos(recipes: List<RecipeDO>) {
        this.recipes = recipes
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }
}