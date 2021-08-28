package com.jakubaniola.myrecipebook.ui.addrecipe

sealed class AddRecipeViewState {
    object BeforeAddRecipe : AddRecipeViewState()
    object AfterAddRecipe : AddRecipeViewState()
    data class AddRecipeError(val errors: List<AddRecipeFieldError>) : AddRecipeViewState()
}