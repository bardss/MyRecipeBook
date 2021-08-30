package com.jakubaniola.myrecipebook.ui.addeditrecipe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jakubaniola.myrecipebook.R
import com.jakubaniola.myrecipebook.customviews.TextField
import com.jakubaniola.myrecipebook.databinding.FragmentAddEditRecipeBinding
import com.jakubaniola.myrecipebook.ui.ArgumentKeys
import com.jakubaniola.myrecipebook.ui.addeditrecipe.AddEditRecipeViewState.*
import com.jakubaniola.myrecipebook.utils.addOnTextChanged
import com.jakubaniola.pickphotoview.PickPhotoActions
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddEditRecipeFragment : Fragment(), PickPhotoActions {

    private val viewModel by viewModel<AddEditRecipeViewModel>()
    private lateinit var binding: FragmentAddEditRecipeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEditRecipeBinding.inflate(inflater, container, false)
        setupRecipeValuesListeners()
        setupViewStateListener()
        setupRecipeObserver()
        setupAddEditRecipeView()
        binding.pickResultPhotoLayout.setPickPhotoFragment(this)
        binding.pickRecipePhotoLayout.setPickPhotoFragment(this)
        return binding.root
    }

    private fun setupAddEditRecipeView() {
        arguments?.let {
            val recipeId = it.getInt(ArgumentKeys.RECIPE_ID, 0)
            if (recipeId != 0) {
                viewModel.viewState.postValue(EditRecipeState)
                viewModel.getRecipeDetails(recipeId)
            } else {
                viewModel.viewState.postValue(AddRecipeState)
            }
        }
    }

    private fun setupRecipeValuesListeners() {
        binding.nameEditText.addOnTextChanged {
            viewModel.name = it
            binding.nameEditText.error = null
        }
        binding.rateEditText.addOnTextChanged {
            viewModel.rate = it
            binding.rateEditText.error = null
        }
        binding.prepTimeEditText.addOnTextChanged { viewModel.prepTime = it }
        binding.linkToRecipeEditText.addOnTextChanged {
            viewModel.urlToRecipe = it
            binding.linkToRecipeEditText.error = null
        }
        binding.recipeEditText.addOnTextChanged { viewModel.recipeText = it }
        binding.ingredientListView.setupIngredientsListView(
            { viewModel.ingredients.add(it) },
            { viewModel.ingredients.remove(it) }
        )
    }

    private fun setupViewStateListener() {
        viewModel.viewState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is AddRecipeState -> setupAddView()
                is EditRecipeState -> setupEditView()
                is AfterAddEditRecipeState -> navigateBack()
                is AddEditRecipeErrorState -> setErrorOnViews(state.errors)
            }
        })
    }

    private fun setErrorOnViews(errors: List<AddEditRecipeFieldError>) {
        errors.forEach {
            when (it) {
                AddEditRecipeFieldError.EMPTY_TITLE ->
                    setErrorOnTextField(binding.nameEditText, R.string.input_cannot_be_empty)
                AddEditRecipeFieldError.INVALID_RATE ->
                    setErrorOnTextField(binding.rateEditText, R.string.invalid_input)
                AddEditRecipeFieldError.INVALID_LINK ->
                    setErrorOnTextField(binding.linkToRecipeEditText, R.string.invalid_input)
            }
        }
    }

    private fun setErrorOnTextField(textField: TextField, errorStringId: Int) {
        textField.error = resources.getString(errorStringId)
    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.pickResultPhotoLayout.onPicturePicked(requestCode, resultCode, data)
        binding.pickRecipePhotoLayout.onPicturePicked(requestCode, resultCode, data)
    }

    override fun setOnCorrectPhotoPickListener(path: String, pickPhotoId: Int?) {
        when (pickPhotoId) {
            binding.pickResultPhotoLayout.pickPhotoViewId -> viewModel.resultPhotoPath = path
            binding.pickRecipePhotoLayout.pickPhotoViewId -> viewModel.recipePhotoPaths.add(path)
        }
    }

    private fun setupRecipeObserver() {
        viewModel.recipe.observe(viewLifecycleOwner, { recipe ->
            binding.nameEditText.text = recipe.name
            binding.rateEditText.text = recipe.rate.toString()
            binding.prepTimeEditText.text = recipe.timeToPrepare
            binding.linkToRecipeEditText.text = recipe.urlToRecipe
            binding.recipeEditText.text = recipe.recipe
            binding.pickResultPhotoLayout.setPictures(listOf(recipe.resultPhotoPath))
            binding.pickRecipePhotoLayout.setPictures(recipe.recipePhotoPaths)
        })
    }

    private fun setupEditView() {
        setupFabOnClick { viewModel.editRecipe() }
    }

    private fun setupAddView() {
        setupFabOnClick { viewModel.addRecipe() }
    }

    private fun setupFabOnClick(action: () -> Unit) {
        binding.fabImageView.setOnClickListener { action() }
    }
}