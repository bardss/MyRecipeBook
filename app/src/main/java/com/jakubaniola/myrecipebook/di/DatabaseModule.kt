package com.jakubaniola.myrecipebook.di

import com.jakubaniola.myrecipebook.database.LocalDatabaseSQL
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { LocalDatabaseSQL.create(androidContext()) }
    single { get<LocalDatabaseSQL>().recipeDao() }
}