package com.jakubaniola.myrecipebook.utils

import android.text.SpannableString
import android.text.style.UnderlineSpan

fun String.underline(start: Int = 0, end: Int = this.length): SpannableString {
    return SpannableString(this).apply {
        setSpan(UnderlineSpan(), start, end, 0)
    }
}