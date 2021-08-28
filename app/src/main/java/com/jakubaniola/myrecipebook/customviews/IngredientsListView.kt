package com.jakubaniola.myrecipebook.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.jakubaniola.myrecipebook.R

class IngredientsListView : LinearLayout {

    private var addImageView: ImageView
    private var ingredientInputEditText: TextField

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        orientation = VERTICAL
        val input = inflate(context, R.layout.layout_ingredient_input, null)
        addView(input)
        ingredientInputEditText = input.findViewById(R.id.ingredient_edit_text)
        addImageView = input.findViewById(R.id.add_image_view)
    }

    fun setupIngredientsListView(
        onAddIngredient: (String) -> Unit,
        onRemoveIngredient: (String) -> Unit
    ) {
        addImageView.setOnClickListener {
            ingredientInputEditText.error = null
            val newIngredient = ingredientInputEditText.text
            if (newIngredient.isNotEmpty()) {
                onAddIngredientAction(onAddIngredient)
                addNextIngredient(onRemoveIngredient)
            } else {
                ingredientInputEditText.error =
                    context.resources.getString(R.string.input_cannot_be_empty)
            }
        }
    }

    private fun onAddIngredientAction(onAddIngredient: (String) -> Unit) {
        val input = ingredientInputEditText.text
        onAddIngredient(input)
    }

    private fun addNextIngredient(onRemoveIngredient: (String) -> Unit) {
        val ingredientView = inflate(context, R.layout.layout_ingredient, null)
        val removeImageView = ingredientView.findViewById<ImageView>(R.id.remove_image_view)
        val ingredientTextView = ingredientView.findViewById<TextView>(R.id.ingredient_text_view)
        ingredientTextView.text = ingredientInputEditText.text
        ingredientInputEditText.text = ""
        removeImageView.setOnClickListener {
            onRemoveIngredient(ingredientTextView, onRemoveIngredient, ingredientView)
        }
        addView(ingredientView)
    }

    private fun onRemoveIngredient(
        ingredientTextView: TextView,
        onRemoveIngredient: (String) -> Unit,
        ingredientView: View?
    ) {
        val ingredient = ingredientTextView.text.toString()
        onRemoveIngredient(ingredient)
        removeView(ingredientView)
    }
}