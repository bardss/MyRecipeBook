package com.jakubaniola.myrecipebook.ui.recipedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakubaniola.myrecipebook.database.databaseobjects.Recipe
import com.jakubaniola.myrecipebook.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    private val _recipe: MutableLiveData<Recipe> = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe>
        get() = _recipe

    fun getRecipeDetails(recipeId: Int) {
        viewModelScope.launch {
            val recipe = recipeRepository.getRecipe(recipeId)
            _recipe.postValue(recipe)
        }
    }
}