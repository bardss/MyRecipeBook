package com.jakubaniola.myrecipebook.ui.addrecipe

import android.webkit.URLUtil
import androidx.lifecycle.*
import com.jakubaniola.myrecipebook.database.databaseobjects.Recipe
import com.jakubaniola.myrecipebook.repository.RecipeRepository
import kotlinx.coroutines.launch

class AddRecipeViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    private val _viewState =
        MutableLiveData<AddRecipeViewState>(AddRecipeViewState.BeforeAddRecipe)
    val viewState: LiveData<AddRecipeViewState>
        get() = _viewState

    var name: String = ""
    var rate: String = ""
    var prepTime: String = ""
    var ingredients: MutableList<String> = mutableListOf()
    var urlToRecipe: String = ""
    var recipe: String = ""
    var resultPhotoPath: String = ""
    var recipePhotoPaths: MutableList<String> = mutableListOf()

    fun addRecipe() {
        val validationErrors = getAddRecipeErrors()
        if (validationErrors.isEmpty()) {
            addRecipeToDatabase()
        } else {
            _viewState.postValue(AddRecipeViewState.AddRecipeError(validationErrors))
        }
    }

    private fun addRecipeToDatabase() {
        viewModelScope.launch {
            val rate = rate.toInt()
            val recipeToAdd = Recipe(
                null,
                name = name,
                rate = rate,
                timeToPrepare = prepTime,
                ingredients = ingredients,
                recipe = recipe,
                urlToRecipe = urlToRecipe,
                resultPhotoPath = resultPhotoPath,
                recipePhotoPaths = recipePhotoPaths
            )
            recipeRepository.addRecipe(recipeToAdd)
        }.invokeOnCompletion {
            _viewState.postValue(AddRecipeViewState.AfterAddRecipe)
        }
    }

    private fun getAddRecipeErrors(): List<AddRecipeFieldError> {
        val errors = mutableListOf<AddRecipeFieldError>()
        if (!isNameValid()) errors.add(AddRecipeFieldError.EMPTY_TITLE)
        if (!isRateValid()) errors.add(AddRecipeFieldError.INVALID_RATE)
        if (!isLinkValid()) errors.add(AddRecipeFieldError.INVALID_LINK)
        return errors
    }

    private fun isNameValid() = name.isNotEmpty()
    private fun isRateValid() = rate.toIntOrNull() != null
    private fun isLinkValid() = urlToRecipe.isEmpty() || URLUtil.isValidUrl(urlToRecipe)
}