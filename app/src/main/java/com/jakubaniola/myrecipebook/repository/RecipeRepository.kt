package com.jakubaniola.myrecipebook.repository

import com.jakubaniola.myrecipebook.database.databaseobjects.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getRecipes(): Flow<List<Recipe>>
    suspend fun addRecipe(recipe: Recipe)
}