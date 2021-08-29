package com.jakubaniola.myrecipebook.ui.addeditrecipe

sealed class AddEditRecipeViewState {
    object AddRecipeState : AddEditRecipeViewState()
    object EditRecipeState : AddEditRecipeViewState()
    object AfterAddEditRecipeState : AddEditRecipeViewState()
    data class AddEditRecipeErrorState(val errors: List<AddEditRecipeFieldError>) : AddEditRecipeViewState()
}