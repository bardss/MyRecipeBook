package com.jakubaniola.myrecipebook.database.dao

import androidx.room.*
import com.jakubaniola.myrecipebook.database.databaseobjects.RecipeDO

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipedo")
    fun getAllRecipes(): List<RecipeDO>

    @Insert
    fun addRecipe(recipe: RecipeDO)
}
