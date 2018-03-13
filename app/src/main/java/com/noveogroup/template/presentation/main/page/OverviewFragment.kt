package com.noveogroup.template.presentation.main.page

import android.app.Activity
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.text.SpannableStringBuilder
import androidx.text.color
import com.noveogroup.template.data.android.system.AlphaColor
import com.noveogroup.template.data.android.system.ResourceManager

interface OverviewFragment {

    val resourceManager: ResourceManager
    val baseActivity: Activity

    private val disabledAlpha: Int
        get() {
            val alphaFloat = resourceManager.resolve(baseActivity, android.R.attr.disabledAlpha) as Float
            return (255 * alphaFloat).toInt()
        }

    @ColorInt
    fun @receiver:AttrRes Int.resolveDisabled(): Int {
        return resourceManager.processColor(AlphaColor(this, disabledAlpha), baseActivity)
    }

    @ColorInt
    fun @receiver:AttrRes Int.resolve(): Int {
        return resourceManager.processColor(AlphaColor(this), baseActivity)
    }

    fun CharSequence.withBlocks(@ColorInt vararg colors: Int) = SpannableStringBuilder().apply {
        colors.forEach {
            color(it) { append('█') }
        }
        append(' ')
        append(this@withBlocks)
    }

    fun CharSequence.colorOf(@AttrRes color: Int) = withBlocks(
            color.resolve()
    )

    fun CharSequence.selectorOf(@AttrRes normal: Int, @AttrRes disabled: Int = normal) = withBlocks(
            normal.resolve(),
            disabled.resolveDisabled()
    )

    fun CharSequence.selectableSelectorOf(@AttrRes normal: Int, @AttrRes active: Int) = withBlocks(
            normal.resolve(),
            normal.resolveDisabled(),
            active.resolve(),
            active.resolveDisabled()
    )

}