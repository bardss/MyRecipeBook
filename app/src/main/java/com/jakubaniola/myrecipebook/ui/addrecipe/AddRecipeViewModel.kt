package com.jakubaniola.myrecipebook.ui.addrecipe

import androidx.lifecycle.*
import com.jakubaniola.myrecipebook.database.databaseobjects.Recipe
import com.jakubaniola.myrecipebook.repository.RecipeRepository
import kotlinx.coroutines.launch

class AddRecipeViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    private val _viewState = MutableLiveData(AddRecipeViewState.BEFORE_ADD_RECIPE)
    var viewState: LiveData<AddRecipeViewState> = _viewState
        get() = _viewState

    var name: String = ""

    fun addRecipe() {
        viewModelScope.launch {
            _viewState.postValue(AddRecipeViewState.LOADING)
            if (isRecipeValuesValid()) {
                val recipeToAdd = Recipe(
                    null,
                    name = name
                )
                recipeRepository.addRecipe(recipeToAdd)
                _viewState.postValue(AddRecipeViewState.AFTER_ADD_RECIPE)
            }
        }
    }

    private fun isRecipeValuesValid() = name.isNotEmpty()
}