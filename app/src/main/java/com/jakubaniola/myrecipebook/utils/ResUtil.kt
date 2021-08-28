package com.jakubaniola.myrecipebook.utils

import android.content.Context
import androidx.core.content.res.ResourcesCompat

object ResUtil {

    fun getDrawable(context: Context, id: Int) =
        ResourcesCompat.getDrawable(context.resources, id, context.theme)
}