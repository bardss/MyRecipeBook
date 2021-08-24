package com.jakubaniola.myrecipebook.database.databaseobjects

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDateTime

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val timeToPrepare: String,
    val rate: Int,
//    val resultPhotos: List<String>,
//    val linkToRecipe: String,
    val ingredients: List<String>,
//    val recipe: String,
//    val recipePhotos: List<String>
)