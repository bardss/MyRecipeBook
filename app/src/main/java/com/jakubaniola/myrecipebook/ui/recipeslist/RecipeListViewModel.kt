package com.jakubaniola.myrecipebook.ui.recipeslist

import androidx.lifecycle.*
import com.jakubaniola.myrecipebook.database.databaseobjects.Recipe
import com.jakubaniola.myrecipebook.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeListViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    val recipes: LiveData<List<Recipe>>
        get() = recipeRepository
            .getRecipes()
            .asLiveData()

    fun refreshRecipes() {
        viewModelScope.launch {
            recipeRepository.getRecipes()
        }
    }
}