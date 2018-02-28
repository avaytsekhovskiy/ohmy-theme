package com.noveogroup.template.presentation.main.page

import android.app.Activity
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import com.noveogroup.template.data.android.system.AlphaColor
import com.noveogroup.template.data.android.system.ResourceManager

interface OverviewFragment {

    val resourceManager: ResourceManager
    val baseActivity: Activity

    fun CharSequence.withBlocks(vararg colors: AlphaColor) = SpannableStringBuilder().apply {
        colors.forEach { append(it.asBlock()) }
        append(' ')
        append(this@withBlocks)
    }

    fun AlphaColor.asBlock() = SpannableString("█").apply {
        val color = resourceManager.processColor(this@asBlock, baseActivity)
        setSpan(ForegroundColorSpan(color), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}