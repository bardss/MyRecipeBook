package com.jakubaniola.myrecipebook.di

import com.jakubaniola.myrecipebook.ui.addeditrecipe.AddEditRecipeViewModel
import com.jakubaniola.myrecipebook.ui.recipedetails.RecipeDetailsViewModel
import com.jakubaniola.myrecipebook.ui.recipeslist.RecipeListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RecipeListViewModel(get()) }
    viewModel { AddEditRecipeViewModel(get()) }
    viewModel { RecipeDetailsViewModel(get()) }
}
