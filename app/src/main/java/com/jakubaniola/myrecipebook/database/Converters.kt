package com.jakubaniola.myrecipebook.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun toToListOfStrings(paths: String): List<String> {
        return paths.split(",").map { it.trim() }
    }

    @TypeConverter
    fun fromListOfStrings(paths: List<String>): String {
        return paths.joinToString(",")
    }
}