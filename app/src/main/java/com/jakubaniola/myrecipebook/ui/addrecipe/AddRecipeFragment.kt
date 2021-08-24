package com.jakubaniola.myrecipebook.ui.addrecipe

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
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddRecipeFragment : Fragment() {

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
        return binding.root
    }

    private fun setupRecipeValuesListeners() {
        binding.nameEditText.addOnTextChanged { viewModel.name = it }
        binding.rateEditText.addOnTextChanged { viewModel.rate = it }
        binding.prepTimeEditText.addOnTextChanged { viewModel.prepTime = it }
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
                LOADING -> {
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
}