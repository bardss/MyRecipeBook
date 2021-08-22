package com.jakubaniola.myrecipebook.database.dao

import androidx.room.*
import com.jakubaniola.myrecipebook.database.databaseobjects.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Insert
    suspend fun addRecipe(recipe: Recipe)
}
