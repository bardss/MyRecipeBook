package com.jakubaniola.myrecipebook.database

import android.content.Context
import android.content.SharedPreferences


private const val SHARED_PREFERENCES_KEY = "my_recipe_book_shared_preferences"

object LocalDatabaseNoSQL {

    private lateinit var database: SharedPreferences

    fun initNoSQLDatabase(context: Context) {
        database = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }

    fun getInstance(): SharedPreferences = database
}