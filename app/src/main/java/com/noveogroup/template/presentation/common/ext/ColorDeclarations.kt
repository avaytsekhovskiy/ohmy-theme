@file:Suppress("unused")

package com.noveogroup.template.presentation.common.ext


import android.content.Context
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.annotation.AttrRes
import android.util.TypedValue

private const val COLOR_COMPONENT_INTENSITY_MAX = 255f

fun Context.getThemeColor(@AttrRes attrId: Int) =
        TypedValue().also { theme.resolveAttribute(attrId, it, true) }.data

fun Context.colorizeDrawable(drawable: Drawable, @AttrRes attrId: Int): Drawable =
        drawable.mutate()
                .apply { setColorFilter(getThemeColor(attrId), PorterDuff.Mode.SRC_ATOP) }

fun Int.toFloatRgbVector() = floatArrayOf(
        Color.red(this).toFloat() / COLOR_COMPONENT_INTENSITY_MAX,
        Color.green(this).toFloat() / COLOR_COMPONENT_INTENSITY_MAX,
        Color.blue(this).toFloat() / COLOR_COMPONENT_INTENSITY_MAX)

fun Int.toColorMatrix() = ColorMatrix().apply {
    val rgb = toFloatRgbVector()
    setSaturation(0f)
    set(floatArrayOf(
            0f, rgb[0], 0f, 0f, 0f,
            0f, 0f, rgb[1], 0f, 0f,
            0f, 0f, 0f, rgb[2], 0f,
            0f, 0f, 0f, 1f, 0f))
}
