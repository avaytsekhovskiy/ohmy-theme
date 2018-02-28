package com.noveogroup.template.data.android.system

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.ColorUtils
import android.util.TypedValue
import javax.inject.Inject

class ResourceManager @Inject constructor(private val context: Context) {
    private val resources = context.resources

    fun getString(@StringRes stringId: Int, vararg args: Any? = arrayOf()): String = when {
        args.isEmpty() -> resources.getString(stringId)
        else -> resources.getString(stringId, args)
    }

    fun getDrawable(@DrawableRes drawableId: Int): Drawable {
        return ContextCompat.getDrawable(context, drawableId)!!
    }

    fun getPlural(@PluralsRes pluralId: Int, quantity: Int, vararg args: Any? = arrayOf()): String = when {
        args.isEmpty() -> resources.getQuantityString(pluralId, quantity)
        else -> resources.getQuantityString(pluralId, quantity, args)
    }

    @ColorInt
    fun getColor(@ColorRes colorId: Int): Int {
        return ContextCompat.getColor(context, colorId)
    }

    fun resolve(context: Context, @AttrRes attrId: Int): Int =
            TypedValue().also { context.theme.resolveAttribute(attrId, it, true) }.data

    @ColorInt
    fun processColor(color: AlphaColor, context: Context): Int = color.run {
        ColorUtils.setAlphaComponent(resolve(context, attrId), alpha)
    }

}
