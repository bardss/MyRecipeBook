package com.jakubaniola.myrecipebook.customviews

import android.content.Context
import android.content.res.TypedArray
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
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
            setAttrTextSize(context)
            setAttrInputType()
            setAttrLines()
            setAttrMaxLines()
            setAttrGravity()
            recycle()
        }
    }

    private fun TypedArray.setAttrGravity() {
        val gravity = getInteger(
            R.styleable.TextField_android_gravity,
            Gravity.START
        )
        editText.gravity = gravity
    }

    private fun TypedArray.setAttrMaxLines() {
        val maxLines = getInteger(
            R.styleable.TextField_android_maxLines,
            InputType.TYPE_CLASS_TEXT
        )
        editText.maxLines = maxLines
    }

    private fun TypedArray.setAttrLines() {
        val lines = getInteger(
            R.styleable.TextField_android_lines,
            1
        )
        editText.setLines(lines)
    }

    private fun TypedArray.setAttrInputType() {
        val inputType = getInteger(
            R.styleable.TextField_android_inputType,
            InputType.TYPE_CLASS_TEXT
        )
        editText.inputType = inputType
    }

    private fun TypedArray.setAttrTextSize(context: Context) {
        val textSize = getDimension(
            R.styleable.TextField_textFieldTextSize,
            context.resources.getDimension(R.dimen.text_size_medium)
        ) / resources.displayMetrics.density
        editText.textSize = textSize
    }

    fun addTextChangedListener(textWatcher: TextWatcher) {
        editText.addTextChangedListener(textWatcher)
    }
}