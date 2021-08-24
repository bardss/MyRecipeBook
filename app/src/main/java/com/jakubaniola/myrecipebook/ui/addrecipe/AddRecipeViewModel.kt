package com.jakubaniola.myrecipebook.ui.addrecipe

import androidx.lifecycle.*
import com.jakubaniola.myrecipebook.database.databaseobjects.Recipe
import com.jakubaniola.myrecipebook.repository.RecipeRepository
import kotlinx.coroutines.launch

class AddRecipeViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    private val _viewState = MutableLiveData(AddRecipeViewState.BEFORE_ADD_RECIPE)
    val viewState: LiveData<AddRecipeViewState>
        get() = _viewState

    var name: String = ""
    var rate: String = ""
    var prepTime: String = ""
    var ingredients: MutableList<String> = mutableListOf()

    fun addRecipe() {
        viewModelScope.launch {
            _viewState.postValue(AddRecipeViewState.LOADING)
            if (isRecipeValuesValid()) {
                val rate = rate.toInt()
                val recipeToAdd = Recipe(
                    null,
                    name = name,
                    rate = rate,
                    timeToPrepare = prepTime,
                    ingredients = ingredients
                )
                recipeRepository.addRecipe(recipeToAdd)
                _viewState.postValue(AddRecipeViewState.AFTER_ADD_RECIPE)
            }
        }
    }

    private fun isRecipeValuesValid() = name.isNotEmpty()
}