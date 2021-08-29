package com.jakubaniola.myrecipebook.ui.recipeslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jakubaniola.myrecipebook.R
import com.jakubaniola.myrecipebook.database.databaseobjects.Recipe
import com.jakubaniola.myrecipebook.utils.underline
import com.jakubaniola.pickphotoview.PickPhotoImageUtil

class RecipesAdapter(
    private val imageUtil: PickPhotoImageUtil,
    private val onRecipeClickAction: (Int) -> Unit
) : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    private var recipes: List<Recipe> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_recipe, parent, false
            )
        )
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipesAdapter.ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.nameTextView.text = recipe.name.underline()
        holder.recipeItemLayout.setOnClickListener {
            recipe.id?.let { onRecipeClickAction(it) }
        }
        setupRateText(recipe, holder)
        setupPrepTime(recipe, holder)
        setupResultImage(recipe, holder)
    }

    private fun setupRateText(recipe: Recipe, holder: ViewHolder) {
        val resources = holder.rateTextView.context.resources
        val rateText = recipe.rate.toString() + resources.getString(R.string.max_value)
        holder.rateTextView.text = rateText
    }

    private fun setupResultImage(recipe: Recipe, holder: ViewHolder) {
        if (recipe.resultPhotoPath.isNotEmpty()) {
            val bitmap = imageUtil.getBitmapFromPath(recipe.resultPhotoPath)
            holder.recipeImageView.setImageDrawable(bitmap)
            setTopMarginForItemWithRecipePhoto(holder, -32)
            holder.recipeImageView.visibility = View.VISIBLE
        } else {
            setTopMarginForItemWithRecipePhoto(holder, 0)
            holder.recipeImageView.visibility = View.GONE
        }
    }

    private fun setTopMarginForItemWithRecipePhoto(holder: ViewHolder, margin: Int) {
        holder.recipeTextsLayout.layoutParams =
            (holder.recipeTextsLayout.layoutParams as ConstraintLayout.LayoutParams).apply {
                topMargin = margin
            }
    }

    private fun setupPrepTime(recipe: Recipe, holder: ViewHolder) {
        if (recipe.timeToPrepare.isNotEmpty()) {
            holder.prepTimeTextView.visibility = View.VISIBLE
            holder.prepTimeLabelTextView.visibility = View.VISIBLE
            holder.prepTimeTextView.text = recipe.timeToPrepare
        } else {
            holder.prepTimeTextView.visibility = View.GONE
            holder.prepTimeLabelTextView.visibility = View.GONE
        }
    }

    fun setRecipes(recipes: List<Recipe>) {
        this.recipes = recipes
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeItemLayout = view.findViewById<ViewGroup>(R.id.recipe_item_layout)
        val nameTextView = view.findViewById<TextView>(R.id.name_text_view)
        val rateTextView = view.findViewById<TextView>(R.id.rate_text_view)
        val prepTimeTextView = view.findViewById<TextView>(R.id.prep_time_text_view)
        val prepTimeLabelTextView = view.findViewById<TextView>(R.id.prep_time_label_text_view)
        val recipeImageView = view.findViewById<ImageView>(R.id.recipe_image_view)
        val recipeTextsLayout = view.findViewById<ConstraintLayout>(R.id.recipe_texts_layout)
    }
}