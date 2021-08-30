package com.jakubaniola.myrecipebook.ui.recipedetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jakubaniola.myrecipebook.R

class IngredientsListAdapter : RecyclerView.Adapter<IngredientsListAdapter.ViewHolder>() {

    private val ingredients = mutableListOf<String>()

    override fun getItemCount(): Int = ingredients.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_ingredient, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = ingredients[position]
        val dash = holder.ingredientTextView.context.resources.getString(R.string.long_dash)
        holder.ingredientTextView.text = " $dash $ingredient"
    }

    fun setIngredients(ingredients: List<String>) {
        this.ingredients.clear()
        this.ingredients.addAll(ingredients)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ingredientTextView = view.findViewById<TextView>(R.id.ingredient_text_view)
    }
}