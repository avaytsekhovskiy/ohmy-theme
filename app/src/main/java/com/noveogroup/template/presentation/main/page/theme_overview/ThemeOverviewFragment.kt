package com.noveogroup.template.presentation.main.page.theme_overview

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.AttrRes
import android.support.v4.graphics.ColorUtils
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
import com.noveogroup.template.data.android.system.ResourceManager
import com.noveogroup.template.presentation.common.android.BaseFragment
import com.noveogroup.template.presentation.common.android.inflater.Layout
import com.noveogroup.template.presentation.di.DI
import kotlinx.android.synthetic.main.fragment_overview.*

@Suppress("UsePropertyAccessSyntax")
@SuppressLint("SetTextI18n")
@Layout(R.layout.fragment_overview)
class ThemeOverviewFragment : BaseFragment(), ThemeOverviewView {

    @InjectPresenter
    lateinit var presenter: ThemeOverviewPresenter

    @ProvidePresenter
    fun providePresenter() = DI.mainScope.getInstance(ThemeOverviewPresenter::class.java)!!

    val resourceManager by lazy { ResourceManager(baseActivity) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SpannableStringBuilder()
                .appendln("BACKGROUNDS")
                .append(span(android.R.attr.colorBackground, "█")).append(' ')
                .appendln("android:colorBackground")
                .append(span(android.R.attr.colorForeground, "█")).append(' ')
                .appendln("android:colorForeground")
                .appendln().appendln("COLORS BRIGHT")
                .append(span(R.attr.colorPrimaryLight, "█")).append(' ')
                .appendln("colorPrimaryLight")
                .append(span(R.attr.colorPrimary, "█")).append(' ')
                .appendln("colorPrimary")
                .append(span(R.attr.colorPrimaryDark, "█")).append(' ')
                .appendln("colorPrimaryDark")
                .append(span(R.attr.colorAccent, "█")).append(' ')
                .appendln("colorAccent")
                .appendln().appendln("COLORS TEXT")
                .append(span(R.attr.textColorError, "█")).append(' ')
                .appendln("textColorError")
                .append(span(android.R.attr.textColorPrimary, "█")).append(' ')
                .appendln("android:textColorPrimary = colorPrimary")
                .append(span(android.R.attr.textColorSecondary, "█")).append(' ')
                .appendln("android:textColorSecondary")
                .append(span(android.R.attr.textColorSecondaryInverse, "█")).append(' ')
                .appendln("android:textColorSecondaryInverse")
                .append(span(android.R.attr.textColorTertiary, "█")).append(' ')
                .appendln("android:textColorTertiary")
                .append(span(android.R.attr.textColorTertiaryInverse, "█")).append(' ')
                .appendln("android:textColorTertiaryInverse")
                .appendln().appendln("SELECTORS")
                .append(span(android.R.attr.textColorSecondary, "█"))
                .append(spanAlpha(128, android.R.attr.textColorSecondary, "█")).append(' ')
                .appendln("android:textColor = secondaryDisabledNatural")
                .append(span(android.R.attr.textColorSecondary, "█"))
                .append(spanAlpha(128, android.R.attr.textColorSecondary, "█")).append(' ')
                .appendln("android:editTextColor = secondaryDisabledNatural")
                .append(span(android.R.attr.textColorTertiary, "█"))
                .append(spanAlpha(128, android.R.attr.textColorTertiary, "█")).append(' ')
                .appendln("android:textColorHint = tertiaryDisabledNatural")
                .append(span(R.attr.colorAccent, "█"))
                .append(spanAlpha(128, R.attr.colorAccent, "█")).append(' ')
                .appendln("android:textColorLink = accentDisabledNatural")
                .let { it as SpannableStringBuilder }
                .let(colorsLabel::setText)
    }

    private fun span(@AttrRes attrId: Int, text: CharSequence): Spannable = SpannableString(text).apply {
        setSpan(ForegroundColorSpan(colorOf(attrId)), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private fun spanAlpha(alpha: Int, @AttrRes attrId: Int, text: CharSequence): Spannable = SpannableString(text).apply {
        val alphaColorInt = ColorUtils.setAlphaComponent(colorOf(attrId), alpha)
        setSpan(ForegroundColorSpan(alphaColorInt), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private fun colorOf(@AttrRes attrId: Int) = resourceManager.resolve(baseActivity, attrId)

    companion object {
        fun newInstance() = ThemeOverviewFragment()
    }
}