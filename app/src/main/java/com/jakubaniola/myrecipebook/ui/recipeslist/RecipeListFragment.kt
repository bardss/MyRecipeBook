package com.jakubaniola.myrecipebook.ui.recipeslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.jakubaniola.myrecipebook.R
import com.jakubaniola.myrecipebook.customviews.TextField
import com.jakubaniola.myrecipebook.databinding.FragmentRecipeListBinding
import com.jakubaniola.myrecipebook.ui.ArgumentKeys
import com.jakubaniola.myrecipebook.ui.recipeslist.RecipeListType.*
import com.jakubaniola.myrecipebook.utils.ResUtil
import com.jakubaniola.myrecipebook.utils.addOnTextChanged
import com.jakubaniola.pickphotoview.PickPhotoImageUtil
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
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
        setupListTypeClick()
        setupSortTypeClick()
        setupRecipeRecyclerView()
        setupRecipeDataObserver()
        setupSortTypeObserver()
        setupListTypeObserver()
        setupSearchView()
        return binding.root
    }

    private fun setupRecipeRecyclerView() {
        adapter = RecipesAdapter(
            PickPhotoImageUtil(binding.recipeRecyclerView.context),
            ::navigateToRecipeDetails
        )
        binding.recipeRecyclerView.layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
    }

    private fun setupListTypeClick() {
        binding.root.findViewById<ImageView>(R.id.list_type_image_view)
            .setOnClickListener { viewModel.toggleListType() }
    }

    private fun setupSortTypeClick() {
        binding.root.findViewById<ImageView>(R.id.sort_type_image_view)
            .setOnClickListener { viewModel.toggleSortType() }
    }

    private fun setupListTypeObserver() {
        viewModel.listType.observe(viewLifecycleOwner, { listType ->
            val listTypeImageView =
                binding.root.findViewById<ImageView>(R.id.list_type_image_view)
            when (listType) {
                LIST -> {
                    val drawable = ResUtil.getDrawable(listTypeImageView.context, R.drawable.ic_list)
                    listTypeImageView.setImageDrawable(drawable)
                    setLayoutManager(LinearLayoutManager(context))
                }
                GRID -> {
                    val drawable = ResUtil.getDrawable(listTypeImageView.context, R.drawable.ic_grid)
                    listTypeImageView.setImageDrawable(drawable)
                    setLayoutManager(StaggeredGridLayoutManager(2, VERTICAL))
                }
            }
        })
    }

    private fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        binding.recipeRecyclerView.layoutManager = layoutManager
        binding.recipeRecyclerView.requestLayout()
    }

    private fun setupSortTypeObserver() {
        viewModel.sortType.observe(viewLifecycleOwner, { listType ->
            val listTypeImageView =
                binding.root.findViewById<ImageView>(R.id.sort_type_image_view)
            when (listType) {
                RecipeSortType.DEFAUlT -> listTypeImageView.setImageDrawable(
                    ResUtil.getDrawable(listTypeImageView.context, R.drawable.ic_star_unfilled)
                )
                RecipeSortType.BY_RATE -> listTypeImageView.setImageDrawable(
                    ResUtil.getDrawable(listTypeImageView.context, R.drawable.ic_star_filled)
                )
            }
        })
    }

    private fun setupRecipeDataObserver() {
        viewModel.filteredRecipes.observe(viewLifecycleOwner, {
            adapter.setRecipes(it)
        })
    }

    private fun setupAddRecipeClick() {
        binding.fabImageView.setOnClickListener {
            findNavController().navigate(R.id.navigation_add_edit_recipe)
        }
    }


    private fun setupSearchView() {
        val searchTextField =
            binding.root.findViewById<TextField>(R.id.search_text_field)
        searchTextField.addOnTextChanged {
            viewModel.onSearch(it)
        }
    }

    private fun navigateToRecipeDetails(recipeId: Int) {
        val arguments = Bundle().apply {
            putInt(ArgumentKeys.RECIPE_ID, recipeId)
        }
        findNavController().navigate(R.id.navigation_recipe_details, arguments)
    }
}