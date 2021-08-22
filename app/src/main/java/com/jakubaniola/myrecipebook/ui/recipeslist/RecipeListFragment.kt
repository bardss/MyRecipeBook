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

class RecipeListFragment : Fragment() {

    private lateinit var viewModel: RecipeListViewModel
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
        viewModel =
            ViewModelProvider(this).get(RecipeListViewModel::class.java)
        if (!this::binding.isInitialized) {
            binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        }
        setupAddTodoClick()
        setupTodoRecyclerView()
        setupTodoData()
        return binding.root
    }

    private fun setupTodoRecyclerView() {
        adapter = RecipesAdapter()
        binding.recipeRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun setupTodoData() {
        viewModel.todos.observe(viewLifecycleOwner, Observer {
            adapter.setTodos(it)
        })
    }

    private fun setupAddTodoClick() {
        binding.addTodoFabImageView.setOnClickListener {
            findNavController().navigate(R.id.navigation_add_todo)
        }
    }

    private fun getTodoAdapter(): RecipesAdapter {
        return binding.recipeRecyclerView.adapter as RecipesAdapter
    }
}