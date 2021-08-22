package com.jakubaniola.myrecipebook.ui.recipeslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakubaniola.myrecipebook.database.databaseobjects.RecipeDO
import com.jakubaniola.myrecipebook.repository.RecipeRepository

class RecipeListViewModel() : ViewModel() {

    private val _todos = MutableLiveData<List<RecipeDO>>()
    val todos: LiveData<List<RecipeDO>>
        get() = _todos
}