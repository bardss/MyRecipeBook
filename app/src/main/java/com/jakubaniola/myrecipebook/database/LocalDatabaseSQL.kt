package com.jakubaniola.myrecipebook.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jakubaniola.myrecipebook.database.dao.RecipeDao
import com.jakubaniola.myrecipebook.database.databaseobjects.RecipeDO

@Database(entities = arrayOf(
    RecipeDO::class
), version = 1)
abstract class LocalDatabaseSQL : RoomDatabase() {
    abstract fun todoDao(): RecipeDao
}
