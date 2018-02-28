package com.noveogroup.template.presentation.main.page.theme_overview

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
import com.noveogroup.template.data.android.system.AlphaColor
import com.noveogroup.template.data.android.system.ResourceManager
import com.noveogroup.template.presentation.common.android.BaseFragment
import com.noveogroup.template.presentation.common.android.inflater.Layout
import com.noveogroup.template.presentation.di.DI
import com.noveogroup.template.presentation.main.page.OverviewFragment
import kotlinx.android.synthetic.main.fragment_overview.*

@Suppress("UsePropertyAccessSyntax")
@SuppressLint("SetTextI18n")
@Layout(R.layout.fragment_overview)
class ThemeOverviewFragment : BaseFragment(), OverviewFragment, ThemeOverviewView {

    @InjectPresenter
    lateinit var presenter: ThemeOverviewPresenter

    @ProvidePresenter
    fun providePresenter() = DI.mainScope.getInstance(ThemeOverviewPresenter::class.java)!!

    override val resourceManager by lazy { ResourceManager(baseActivity) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SpannableStringBuilder()
                .appendln("BACKGROUNDS")
                .appendln("android:colorBackground".withBlocks(
                        AlphaColor(android.R.attr.colorBackground)
                ))
                .appendln("android:colorForeground".withBlocks(
                        AlphaColor(android.R.attr.colorForeground)
                ))
                .appendln().appendln("COLORS BRIGHT")
                .appendln("colorPrimaryLight".withBlocks(
                        AlphaColor(R.attr.colorPrimaryLight)
                ))
                .appendln("colorPrimary".withBlocks(
                        AlphaColor(R.attr.colorPrimary)
                ))
                .appendln("colorPrimaryDark".withBlocks(
                        AlphaColor(R.attr.colorPrimaryDark)
                ))
                .appendln("colorAccent".withBlocks(
                        AlphaColor(R.attr.colorAccent)
                ))
                .appendln().appendln("COLORS TEXT")
                .appendln("textColorError".withBlocks(
                        AlphaColor(R.attr.textColorError)
                ))
                .appendln("android:textColorPrimary = colorPrimary".withBlocks(
                        AlphaColor(android.R.attr.textColorPrimary)
                ))
                .appendln("android:textColorSecondary".withBlocks(
                        AlphaColor(android.R.attr.textColorSecondary)
                ))
                .appendln("android:textColorSecondaryInverse".withBlocks(
                        AlphaColor(android.R.attr.textColorSecondaryInverse)
                ))
                .appendln("android:textColorTertiary".withBlocks(
                        AlphaColor(android.R.attr.textColorTertiary)
                ))
                .appendln("android:textColorTertiaryInverse".withBlocks(
                        AlphaColor(android.R.attr.textColorTertiaryInverse)
                ))
                .appendln().appendln("SELECTORS")
                .appendln("android:textColor = secondaryDisabledNatural".withBlocks(
                        AlphaColor(android.R.attr.textColorSecondary),
                        AlphaColor(android.R.attr.textColorSecondary, 128)
                ))
                .appendln("android:editTextColor = secondaryDisabledNatural".withBlocks(
                        AlphaColor(android.R.attr.textColorSecondary),
                        AlphaColor(android.R.attr.textColorSecondary, 128)
                ))
                .appendln("android:textColorHint = tertiaryDisabledNatural".withBlocks(
                        AlphaColor(android.R.attr.textColorTertiary),
                        AlphaColor(android.R.attr.textColorTertiary, 128)
                ))
                .appendln("android:textColorLink = accentDisabledNatural".withBlocks(
                        AlphaColor(R.attr.colorAccent),
                        AlphaColor(R.attr.colorAccent, 128)
                ))
                .let { it as SpannableStringBuilder }
                .let(colorsLabel::setText)
    }

    companion object {
        fun newInstance() = ThemeOverviewFragment()
    }
}