package com.judekwashie.contactbook.utils

import android.content.Context
import com.judekwashie.contactbook.R
import java.util.Random

fun getRandomColor(context: Context): Int {
    val viewColors = context.resources.getIntArray(R.array.view_colors)
    return viewColors[Random().nextInt(viewColors.size)]
}
