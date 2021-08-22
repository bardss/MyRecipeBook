package com.jakubaniola.myrecipebook.database.databaseobjects

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeDO(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)