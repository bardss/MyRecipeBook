package com.jakubaniola.myrecipebook.ui.recipeslist

import androidx.lifecycle.*
import com.jakubaniola.myrecipebook.database.DatabaseKeys
import com.jakubaniola.myrecipebook.database.LocalDatabaseNoSQL
import com.jakubaniola.myrecipebook.database.databaseobjects.Recipe
import com.jakubaniola.myrecipebook.repository.RecipeRepository
import com.jakubaniola.myrecipebook.ui.recipeslist.RecipeListType.GRID
import com.jakubaniola.myrecipebook.ui.recipeslist.RecipeListType.LIST
import com.jakubaniola.myrecipebook.ui.recipeslist.RecipeSortType.BY_RATE
import com.jakubaniola.myrecipebook.ui.recipeslist.RecipeSortType.DEFAUlT
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

private const val SEARCH_DELAY_MILLIS = 750L
private const val MIN_QUERY_LENGTH = 3

@FlowPreview
class RecipeListViewModel(recipeRepository: RecipeRepository) : ViewModel() {

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

    private val searchQuery = MutableLiveData("")
    private var debouncedSearchQuery: LiveData<String> = searchQuery
        .asFlow()
        .debounce(SEARCH_DELAY_MILLIS)
        .filter { it.length >= MIN_QUERY_LENGTH || it.isEmpty() }
        .asLiveData()

    val filteredRecipes = MediatorLiveData<List<Recipe>>()

    init {
        setupRecipesSources()
    }

    private fun setupRecipesSources() {
        filteredRecipes.addSource(recipes) {
            filteredRecipes.value = filterRecipes(_sortType.value, debouncedSearchQuery.value, it)
        }
        filteredRecipes.addSource(_sortType) {
            if (recipes.value != null)
                filteredRecipes.value = filterRecipes(it, debouncedSearchQuery.value, recipes.value)
        }
        filteredRecipes.addSource(debouncedSearchQuery) {
            filteredRecipes.value = filterRecipes(_sortType.value, it, recipes.value)
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
    }

    fun onSearch(newSearchQuery: String) {
        searchQuery.postValue(newSearchQuery)
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

    private fun filterRecipes(
        sortType: RecipeSortType?,
        searchQuery: String?,
        recipes: List<Recipe>?
    ): List<Recipe> {
        val searchQuery = searchQuery?.lowercase() ?: ""
        val filteredRecipes = recipes?.filter {
            it.name.lowercase().contains(searchQuery)
        } ?: listOf()
        return if (sortType == BY_RATE) {
            filteredRecipes.sortedByDescending { it.rate }
        } else filteredRecipes
    }
}