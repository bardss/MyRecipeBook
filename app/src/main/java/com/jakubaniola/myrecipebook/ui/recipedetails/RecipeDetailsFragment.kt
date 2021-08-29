package com.jakubaniola.myrecipebook.ui.recipedetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jakubaniola.myrecipebook.R
import com.jakubaniola.myrecipebook.database.databaseobjects.Recipe
import com.jakubaniola.myrecipebook.databinding.FragmentRecipeDetailsBinding
import com.jakubaniola.myrecipebook.ui.ArgumentKeys
import com.jakubaniola.myrecipebook.utils.underline
import com.jakubaniola.pickphotoview.PickPhotoActions
import org.koin.androidx.viewmodel.ext.android.viewModel


class RecipeDetailsFragment : Fragment(), PickPhotoActions {

    private val viewModel by viewModel<RecipeDetailsViewModel>()
    private lateinit var binding: FragmentRecipeDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
        setupRecipeDetailsObserver()
        getRecipeDetails()
        setupEditFabClick()
        return binding.root
    }

    private fun setupRecipeDetailsObserver() {
        viewModel.recipe.observe(viewLifecycleOwner, { recipe ->
            setRecipeDetailsOnView(recipe)
        })
    }

    private fun getRecipeDetails() {
        arguments?.let {
            val recipeId = it.getInt(ArgumentKeys.RECIPE_ID)
            viewModel.getRecipeDetails(recipeId)
        }
    }

    private fun setRecipeDetailsOnView(recipe: Recipe) {
        binding.nameTextView.text = recipe.name.underline()
        binding.pickResultPhotoLayout.setPictures(listOf(recipe.resultPhotoPath))
        setPrepTime(recipe.timeToPrepare)
        binding.rateTextView.text = recipe.rate.toString()
        setLinkToRecipe(recipe.urlToRecipe)
        setRecipe(recipe.recipe)
        binding.pickRecipePhotoLayout.setPictures(recipe.recipePhotoPaths)
    }

    private fun setPrepTime(prepTime: String) {
        if (prepTime.isNotEmpty()) {
            binding.prepTimeTextView.text = prepTime
        } else {
            val longDash = resources.getString(R.string.long_dash)
            binding.prepTimeTextView.text = " $longDash "
        }
    }

    private fun setLinkToRecipe(linkToRecipe: String) {
        if (linkToRecipe.isNotEmpty()) {
            binding.linkToRecipeTextView.text = linkToRecipe.underline()
            binding.linkToRecipeTextView.setOnClickListener { openUrl(linkToRecipe) }
        } else {
            binding.linkToRecipeTextView.visibility = View.GONE
            binding.linkToRecipeLabelTextView.visibility = View.GONE
        }
    }

    private fun setRecipe(recipe: String) {
        if (recipe.isNotEmpty()) {
            binding.recipeTextView.text = recipe
        } else {
            binding.recipeTextView.visibility = View.GONE
            binding.recipeLabelTextView.visibility = View.GONE
        }
    }

    private fun openUrl(linkToRecipe: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(linkToRecipe))
        startActivity(browserIntent)
    }

    private fun setupEditFabClick() {
        binding.editRecipeFabImageView.setOnClickListener {
            navigateToEditRecipeDetails()
        }
    }

    private fun navigateToEditRecipeDetails() {
        val recipeId = viewModel.recipe.value?.id
        recipeId?.let {
            val arguments = Bundle().apply {
                putInt(ArgumentKeys.RECIPE_ID, it)
            }
            findNavController().navigate(R.id.navigation_add_edit_recipe, arguments)
        }
    }
}