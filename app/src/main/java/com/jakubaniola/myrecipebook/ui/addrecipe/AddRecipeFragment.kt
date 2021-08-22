package com.jakubaniola.myrecipebook.ui.addrecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jakubaniola.myrecipebook.R

class AddRecipeFragment : Fragment() {

    private lateinit var addRecipeViewModel: AddRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addRecipeViewModel =
            ViewModelProvider(this).get(AddRecipeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_add_recipe, container, false)
    }
}