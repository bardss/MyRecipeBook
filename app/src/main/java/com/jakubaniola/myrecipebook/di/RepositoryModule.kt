package com.jakubaniola.myrecipebook.di

import com.jakubaniola.myrecipebook.repository.RecipeRepository
import com.jakubaniola.myrecipebook.repository.RecipeRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<RecipeRepository> {
        RecipeRepositoryImpl(get())
    }
}