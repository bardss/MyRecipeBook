package com.jakubaniola.myrecipebook.ui.recipeslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jakubaniola.myrecipebook.R
import com.jakubaniola.myrecipebook.database.databaseobjects.Recipe

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    private var recipes: List<Recipe> = listOf()

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
        holder.nameTextView.text = recipe.name
        holder.rateTextView.text = recipe.rate.toString()
        holder.prepTimeTextView.text = recipe.timeToPrepare
        holder.resultPhotoPath.text = recipe.resultPhotoPath
    }

    fun setRecipes(recipes: List<Recipe>) {
        this.recipes = recipes
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView = view.findViewById<TextView>(R.id.name_text_view)
        val rateTextView = view.findViewById<TextView>(R.id.rate_text_view)
        val prepTimeTextView = view.findViewById<TextView>(R.id.prep_time_text_view)
        val resultPhotoPath = view.findViewById<TextView>(R.id.result_photo_text_view)
    }
}