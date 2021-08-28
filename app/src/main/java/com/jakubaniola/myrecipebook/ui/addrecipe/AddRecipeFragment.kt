package com.jakubaniola.myrecipebook.ui.addrecipe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jakubaniola.myrecipebook.R
import com.jakubaniola.myrecipebook.databinding.FragmentAddRecipeBinding
import com.jakubaniola.myrecipebook.ui.addrecipe.AddRecipeViewState.*
import com.jakubaniola.myrecipebook.utils.addOnTextChanged
import com.jakubaniola.pickphotoview.PickPhotoActions
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddRecipeFragment : Fragment(), PickPhotoActions {

    private val viewModel by viewModel<AddRecipeViewModel>()
    private lateinit var binding: FragmentAddRecipeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddRecipeBinding.inflate(inflater, container, false)
        setupRecipeValuesListeners()
        setupOnClicks()
        setupViewStateListener()
        binding.pickResultPhotoLayout.setPickPhotoFragment(this)
        binding.pickRecipePhotoLayout.setPickPhotoFragment(this)
        return binding.root
    }

    private fun setupRecipeValuesListeners() {
        binding.nameEditText.addOnTextChanged { viewModel.name = it }
        binding.rateEditText.addOnTextChanged { viewModel.rate = it }
        binding.prepTimeEditText.addOnTextChanged { viewModel.prepTime = it }
        binding.linkToRecipeEditText.addOnTextChanged { viewModel.linkToRecipe = it }
        binding.recipeEditText.addOnTextChanged { viewModel.recipe = it }
        binding.ingredientListView.setupIngredientsListView(
            { viewModel.ingredients.add(it) },
            { viewModel.ingredients.remove(it) }
        )
    }

    private fun setupOnClicks() {
        binding.addRecipeFabImageView.setOnClickListener {
            viewModel.addRecipe()
        }
    }

    private fun setupViewStateListener() {
        viewModel.viewState.observe(viewLifecycleOwner, { state ->
            when (state) {
                BEFORE_ADD_RECIPE -> {
                }
                ERROR -> {
                }
                AFTER_ADD_RECIPE -> {
                    navigateBack()
                }
            }
        })
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.navigation_recipe_list)
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
}