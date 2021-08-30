package com.jakubaniola.myrecipebook.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun toToListOfStrings(paths: String): List<String> {
        var list = paths.split(",").map { it.trim() }
        if (list.size == 1 && list[0].isEmpty()) list = listOf()
        return list
    }

    @TypeConverter
    fun fromListOfStrings(paths: List<String>): String {
        return paths.joinToString(",")
    }
}