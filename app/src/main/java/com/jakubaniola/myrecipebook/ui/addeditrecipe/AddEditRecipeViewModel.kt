package com.jakubaniola.myrecipebook.ui.addeditrecipe

import android.util.Patterns
import android.webkit.URLUtil
import androidx.lifecycle.*
import com.jakubaniola.myrecipebook.database.databaseobjects.Recipe
import com.jakubaniola.myrecipebook.repository.RecipeRepository
import com.jakubaniola.myrecipebook.utils.UrlUtil
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

    fun deleteRecipe() {
        viewModelScope.launch {
            recipe.value?.let {
                recipeRepository.deleteRecipe(it)
            }
        }.invokeOnCompletion {
            viewState.postValue(AddEditRecipeViewState.AfterDeleteRecipeState)
        }
    }

    fun addRecipe() {
        validateRecipeErrors(
            onValidAction = { addRecipeToDatabase() },
            onInvalidAction = {
                viewState.postValue(AddEditRecipeViewState.AddEditRecipeErrorState(it))
            }
        )
    }

    fun editRecipe() {
        validateRecipeErrors(
            onValidAction = { editRecipeInDatabase() },
            onInvalidAction = {
                viewState.postValue(AddEditRecipeViewState.AddEditRecipeErrorState(it))
            }
        )
    }

    private fun validateRecipeErrors(
        onValidAction: () -> Unit,
        onInvalidAction: (List<AddEditRecipeFieldError>) -> Unit
    ) {
        val validationErrors = getAddEditRecipeErrors()
        if (validationErrors.isEmpty()) {
            onValidAction()
        } else {
            onInvalidAction(validationErrors)
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
            val recipeToAdd = getRecipeToAddOrEdit()
            recipeRepository.addRecipe(recipeToAdd)
        }.invokeOnCompletion {
            viewState.postValue(AddEditRecipeViewState.AfterAddEditRecipeState)
        }
    }

    private fun editRecipeInDatabase() {
        viewModelScope.launch {
            val recipeToEdit = getRecipeToAddOrEdit(recipe.value?.id)
            recipeRepository.editRecipe(recipeToEdit)
        }.invokeOnCompletion {
            viewState.postValue(AddEditRecipeViewState.AfterAddEditRecipeState)
        }
    }

    private fun getRecipeToAddOrEdit(recipeId: Int? = null): Recipe {
        val rate = rate.toInt()
        return Recipe(
            id = recipeId,
            name = name,
            rate = rate,
            timeToPrepare = prepTime,
            ingredients = ingredients,
            recipe = recipeText,
            urlToRecipe = UrlUtil().prepareUrlToOpenInBrowser(urlToRecipe),
            resultPhotoPath = resultPhotoPath,
            recipePhotoPaths = recipePhotoPaths
        )
    }

    private fun getAddEditRecipeErrors(): List<AddEditRecipeFieldError> {
        val errors = mutableListOf<AddEditRecipeFieldError>()
        if (!isNameValid()) errors.add(AddEditRecipeFieldError.EMPTY_TITLE)
        if (!isRateValid()) errors.add(AddEditRecipeFieldError.INVALID_RATE)
        if (!isLinkValid()) errors.add(AddEditRecipeFieldError.INVALID_LINK)
        return errors
    }

    private fun isNameValid() = name.isNotEmpty()
    private fun isRateValid() = rate.toIntOrNull() != null
    private fun isLinkValid() =
        urlToRecipe.isEmpty() || Patterns.WEB_URL.matcher(urlToRecipe).matches()

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