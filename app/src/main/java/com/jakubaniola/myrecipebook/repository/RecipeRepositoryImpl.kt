package com.jakubaniola.myrecipebook.repository

import com.jakubaniola.myrecipebook.database.dao.RecipeDao
import com.jakubaniola.myrecipebook.database.databaseobjects.Recipe
import kotlinx.coroutines.flow.Flow

class RecipeRepositoryImpl(private val recipeDao: RecipeDao) : RecipeRepository {

    override fun getRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes()

    override suspend fun getRecipe(recipeId: Int): Recipe = recipeDao.getRecipe(recipeId)

    override suspend fun addRecipe(recipe: Recipe) {
        recipeDao.addRecipe(recipe)
    }

    override suspend fun editRecipe(recipe: Recipe) {
        recipeDao.editRecipe(recipe)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }
}