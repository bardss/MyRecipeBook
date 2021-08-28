package com.jakubaniola.myrecipebook.ui.recipeslist

import android.provider.MediaStore
import androidx.lifecycle.*
import com.jakubaniola.myrecipebook.database.DatabaseKeys
import com.jakubaniola.myrecipebook.database.LocalDatabaseNoSQL
import com.jakubaniola.myrecipebook.database.databaseobjects.Recipe
import com.jakubaniola.myrecipebook.repository.RecipeRepository
import com.jakubaniola.myrecipebook.ui.recipeslist.RecipeListType.*
import com.jakubaniola.myrecipebook.ui.recipeslist.RecipeSortType.*
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform

class RecipeListViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    private var _listType = MutableLiveData(getListTypeFromDatabase())
    val listType: LiveData<RecipeListType>
        get() = _listType

    private var _sortType = MutableLiveData(getSortTypeFromDatabase())
    val sortType: LiveData<RecipeSortType>
        get() = _sortType

    private val recipes: LiveData<List<Recipe>> =
        recipeRepository
            .getRecipes()
            .asLiveData()

    val sortedRecipes = MediatorLiveData<List<Recipe>>()

    init {
        sortedRecipes.addSource(recipes) {
            sortedRecipes.value = sortRecipes(_sortType.value, it)
        }
    }

    fun toggleListType() {
        val newListType = when (_listType.value) {
            LIST -> GRID
            GRID -> LIST
            else -> LIST
        }
        _listType.postValue(newListType)
        putListTypeToDatabase(newListType)
    }

    fun toggleSortType() {
        val newSortType = when (_sortType.value) {
            DEFAUlT -> BY_RATE
            BY_RATE -> DEFAUlT
            null -> DEFAUlT
        }
        _sortType.postValue(newSortType)
        putSortTypeToDatabase(newSortType)
        sortedRecipes.value = sortRecipes(newSortType, recipes.value)
    }

    private fun putListTypeToDatabase(listType: RecipeListType) {
        LocalDatabaseNoSQL.getInstance().edit().apply {
            putInt(DatabaseKeys.LIST_TYPE, listType.ordinal)
            apply()
        }
    }

    private fun putSortTypeToDatabase(sortType: RecipeSortType) {
        LocalDatabaseNoSQL.getInstance().edit().apply {
            putInt(DatabaseKeys.SORT_TYPE, sortType.ordinal)
            apply()
        }
    }

    private fun getListTypeFromDatabase(): RecipeListType =
        RecipeListType.values()[LocalDatabaseNoSQL.getInstance().getInt(DatabaseKeys.LIST_TYPE, 0)]

    private fun getSortTypeFromDatabase(): RecipeSortType =
        RecipeSortType.values()[LocalDatabaseNoSQL.getInstance().getInt(DatabaseKeys.SORT_TYPE, 0)]

    private fun sortRecipes(sortType: RecipeSortType?, recipes: List<Recipe>?): List<Recipe>? {
        return if (sortType == BY_RATE) {
            recipes?.sortedByDescending { it.rate }
        } else recipes
    }
}