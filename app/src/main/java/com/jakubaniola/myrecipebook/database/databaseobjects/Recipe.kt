package com.jakubaniola.myrecipebook.database.databaseobjects

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String
)