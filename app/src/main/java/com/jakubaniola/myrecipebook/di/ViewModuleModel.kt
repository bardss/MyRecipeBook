package com.jakubaniola.myrecipebook.di

import com.jakubaniola.myrecipebook.ui.addrecipe.AddRecipeViewModel
import com.jakubaniola.myrecipebook.ui.recipeslist.RecipeListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RecipeListViewModel(get()) }
    viewModel { AddRecipeViewModel(get()) }
}
