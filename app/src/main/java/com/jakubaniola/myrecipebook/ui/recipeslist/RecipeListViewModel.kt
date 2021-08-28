package com.jakubaniola.myrecipebook.ui.recipeslist

import androidx.lifecycle.*
import com.jakubaniola.myrecipebook.database.DatabaseKeys
import com.jakubaniola.myrecipebook.database.LocalDatabaseNoSQL
import com.jakubaniola.myrecipebook.database.LocalDatabaseSQL
import com.jakubaniola.myrecipebook.database.databaseobjects.Recipe
import com.jakubaniola.myrecipebook.repository.RecipeRepository
import com.jakubaniola.myrecipebook.ui.recipeslist.ListType.*
import kotlinx.coroutines.launch

class RecipeListViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {


    private var _listType = MutableLiveData(getListTypeFromDatabase())
        set(value) {
            LocalDatabaseNoSQL.getInstance().edit().apply {
                val listType = value.value?.ordinal ?: LIST.ordinal
                putInt(DatabaseKeys.LIST_TYPE, listType)
                apply()
            }
            field = value
        }
    val listType: LiveData<ListType>
        get() = _listType


    val recipes: LiveData<List<Recipe>>
        get() = recipeRepository
            .getRecipes()
            .asLiveData()

    fun toggleListType() {
        val newListType = when (_listType.value) {
            LIST -> GRID
            GRID -> LIST
            else -> LIST
        }
        _listType.postValue(newListType)
        putListTypeToDatabase(newListType)
    }

    private fun putListTypeToDatabase(listType: ListType) {
        LocalDatabaseNoSQL.getInstance().edit().apply {
            putInt(DatabaseKeys.LIST_TYPE, listType.ordinal)
            apply()
        }
    }

    private fun getListTypeFromDatabase(): ListType =
        ListType.values()[LocalDatabaseNoSQL.getInstance().getInt(DatabaseKeys.LIST_TYPE, 0)]
}