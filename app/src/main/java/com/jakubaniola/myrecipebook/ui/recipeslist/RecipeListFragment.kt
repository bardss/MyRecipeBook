package com.jakubaniola.myrecipebook.ui.recipeslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakubaniola.myrecipebook.R
import com.jakubaniola.myrecipebook.databinding.FragmentRecipeListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecipeListFragment : Fragment() {

    private val viewModel by viewModel<RecipeListViewModel>()
    private lateinit var binding: FragmentRecipeListBinding
    private var adapter: RecipesAdapter
        get() = binding.recipeRecyclerView.adapter as RecipesAdapter
        set(value) {
            binding.recipeRecyclerView.adapter = value
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        setupAddRecipeClick()
        setupRecipeRecyclerView()
        setupRecipeData()
        return binding.root
    }

    private fun setupRecipeRecyclerView() {
        adapter = RecipesAdapter()
        binding.recipeRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun setupRecipeData() {
        viewModel.recipes.observe(viewLifecycleOwner, {
            adapter.setRecipes(it)
        })
    }

    private fun setupAddRecipeClick() {
        binding.addRecipeFabImageView.setOnClickListener {
            findNavController().navigate(R.id.navigation_add_recipe)
        }
    }
}