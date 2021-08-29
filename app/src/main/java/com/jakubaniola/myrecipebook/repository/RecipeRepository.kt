package com.jakubaniola.myrecipebook.repository

import com.jakubaniola.myrecipebook.database.databaseobjects.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getRecipes(): Flow<List<Recipe>>
    suspend fun getRecipe(recipeId: Int): Recipe
    suspend fun addRecipe(recipe: Recipe)
}