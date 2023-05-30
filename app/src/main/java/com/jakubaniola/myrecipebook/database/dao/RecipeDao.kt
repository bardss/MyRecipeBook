package com.jakubaniola.myrecipebook.database.dao

import androidx.room.*
import com.jakubaniola.myrecipebook.database.databaseobjects.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE id IS :recipeId")
    fun getRecipe(recipeId: Int): Recipe

    @Insert
    fun addRecipe(recipe: Recipe)

    @Update
    fun editRecipe(recipe: Recipe)

    @Delete
    fun deleteRecipe(recipe: Recipe)
}
