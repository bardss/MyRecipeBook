package com.jakubaniola.myrecipebook.ui.addeditrecipe

import android.webkit.URLUtil
import androidx.lifecycle.*
import com.jakubaniola.myrecipebook.database.databaseobjects.Recipe
import com.jakubaniola.myrecipebook.repository.RecipeRepository
import kotlinx.coroutines.launch

class AddEditRecipeViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    val viewState =
        MutableLiveData<AddEditRecipeViewState>(AddEditRecipeViewState.AddRecipeState)

    private val _recipe: MutableLiveData<Recipe> = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe>
        get() = _recipe

    var name: String = ""
    var rate: String = ""
    var prepTime: String = ""
    var ingredients: MutableList<String> = mutableListOf()
    var urlToRecipe: String = ""
    var recipeText: String = ""
    var resultPhotoPath: String = ""
    var recipePhotoPaths: MutableList<String> = mutableListOf()

    fun addRecipe() {
        val validationErrors = getAddRecipeErrors()
        if (validationErrors.isEmpty()) {
            addRecipeToDatabase()
        } else {
            viewState.postValue(AddEditRecipeViewState.AddEditRecipeErrorState(validationErrors))
        }
    }

    fun getRecipeDetails(recipeId: Int) {
        viewModelScope.launch {
            val recipe = recipeRepository.getRecipe(recipeId)
            _recipe.postValue(recipe)
            setRecipeDetails(recipe)
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
                recipe = recipeText,
                urlToRecipe = urlToRecipe,
                resultPhotoPath = resultPhotoPath,
                recipePhotoPaths = recipePhotoPaths
            )
            recipeRepository.addRecipe(recipeToAdd)
        }.invokeOnCompletion {
            viewState.postValue(AddEditRecipeViewState.AfterAddEditRecipeState)
        }
    }

    private fun getAddRecipeErrors(): List<AddEditRecipeFieldError> {
        val errors = mutableListOf<AddEditRecipeFieldError>()
        if (!isNameValid()) errors.add(AddEditRecipeFieldError.EMPTY_TITLE)
        if (!isRateValid()) errors.add(AddEditRecipeFieldError.INVALID_RATE)
        if (!isLinkValid()) errors.add(AddEditRecipeFieldError.INVALID_LINK)
        return errors
    }

    private fun isNameValid() = name.isNotEmpty()
    private fun isRateValid() = rate.toIntOrNull() != null
    private fun isLinkValid() = urlToRecipe.isEmpty() || URLUtil.isValidUrl(urlToRecipe)

    private fun setRecipeDetails(recipe: Recipe) {
        name = recipe.name
        rate = recipe.rate.toString()
        prepTime = recipe.timeToPrepare
        ingredients = recipe.ingredients.toMutableList()
        urlToRecipe = recipe.urlToRecipe
        recipeText = recipe.recipe
        resultPhotoPath = recipe.resultPhotoPath
        recipePhotoPaths = recipe.recipePhotoPaths.toMutableList()
    }
}