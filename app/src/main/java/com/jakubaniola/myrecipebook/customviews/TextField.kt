package com.jakubaniola.myrecipebook.customviews

import android.content.Context
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import android.widget.FrameLayout
import com.google.android.material.textfield.TextInputLayout
import com.jakubaniola.myrecipebook.R

class TextField : FrameLayout {

    private val editText: EditText
    private val inputLayout: TextInputLayout

    var text: String
        set(value) {
            editText.setText(value)
        }
        get() = editText.text?.toString() ?: ""

    var error: String?
        set(value) {
            inputLayout.error = value
        }
        get() = inputLayout.error?.toString()

    init {
        inflate(context, R.layout.layout_text_field, this)
        inputLayout = findViewById(R.id.text_input_layout)
        editText = findViewById(R.id.edit_text)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        saveAttributes(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        saveAttributes(context, attrs)
    }

    private fun saveAttributes(
        context: Context,
        attrs: AttributeSet?
    ) {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.TextField, 0, 0
        ).apply {
            val textSize = getDimension(
                R.styleable.TextField_textFieldTextSize,
                context.resources.getDimension(R.dimen.text_size_medium)
            ) / resources.displayMetrics.density
            editText.textSize = textSize
            val inputType = getInteger(
                R.styleable.TextField_android_inputType,
                InputType.TYPE_CLASS_TEXT
            )
            editText.inputType = inputType
            recycle()
        }
    }

    fun addTextChangedListener(textWatcher: TextWatcher) {
        editText.addTextChangedListener(textWatcher)
    }
}