package com.jakubaniola.myrecipebook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jakubaniola.myrecipebook.database.dao.RecipeDao
import com.jakubaniola.myrecipebook.database.databaseobjects.Recipe

private const val DB_NAME = "myrecipebook_database"

@Database(entities = arrayOf(
    Recipe::class
), version = 1)
abstract class LocalDatabaseSQL : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        fun create(context: Context): LocalDatabaseSQL {
            return Room.databaseBuilder(
                context,
                LocalDatabaseSQL::class.java,
                DB_NAME
            ).build()
        }
    }
}
