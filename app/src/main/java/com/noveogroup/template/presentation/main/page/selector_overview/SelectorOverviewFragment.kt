package com.noveogroup.template.presentation.main.page.selector_overview

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
class SelectorOverviewFragment : BaseFragment(), OverviewFragment, SelectorOverviewView {

    @InjectPresenter
    lateinit var presenter: SelectorOverviewPresenter

    @ProvidePresenter
    fun providePresenter() = DI.mainScope.getInstance(SelectorOverviewPresenter::class.java)!!

    override val resourceManager by lazy { ResourceManager(baseActivity) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SpannableStringBuilder()
                .appendln("NAME: ohmy_cs_*")
                .appendln().appendln("SELECTABLE SELECTORS")
                .appendln().appendln("color blocks order:")
                .appendln("1. regular")
                .appendln("2. regular disabled")
                .appendln("3. selected")
                .appendln("4. selected disabled")
                .appendln()
                .appendln("primary_selected_accent".withBlocks(
                        AlphaColor(R.attr.colorPrimary),
                        AlphaColor(R.attr.colorPrimary, 128),
                        AlphaColor(R.attr.colorAccent),
                        AlphaColor(R.attr.colorAccent, 128)
                ))
                .appendln("primary_dark_selected_accent".withBlocks(
                        AlphaColor(R.attr.colorPrimaryDark),
                        AlphaColor(R.attr.colorPrimaryDark, 128),
                        AlphaColor(R.attr.colorAccent),
                        AlphaColor(R.attr.colorAccent, 128)
                ))
                .appendln("primary_dark_selected_inverse".withBlocks(
                        AlphaColor(R.attr.colorPrimaryDark),
                        AlphaColor(R.attr.colorPrimaryDark, 128),
                        AlphaColor(R.attr.colorPrimaryLight),
                        AlphaColor(R.attr.colorPrimaryLight, 128)
                ))
                .appendln("primary_light_selected_accent".withBlocks(
                        AlphaColor(R.attr.colorPrimaryLight),
                        AlphaColor(R.attr.colorPrimaryLight, 128),
                        AlphaColor(R.attr.colorAccent),
                        AlphaColor(R.attr.colorAccent, 128)
                ))
                .appendln("primary_light_selected_inverse".withBlocks(
                        AlphaColor(R.attr.colorPrimaryLight),
                        AlphaColor(R.attr.colorPrimaryLight, 128),
                        AlphaColor(R.attr.colorPrimaryDark),
                        AlphaColor(R.attr.colorPrimaryDark, 128)
                ))
                .appendln("secondary_selected_accent".withBlocks(
                        AlphaColor(android.R.attr.textColorSecondary),
                        AlphaColor(android.R.attr.textColorSecondary, 128),
                        AlphaColor(R.attr.colorAccent),
                        AlphaColor(R.attr.colorAccent, 128)
                ))
                .appendln("secondary_selected_inverse".withBlocks(
                        AlphaColor(android.R.attr.textColorSecondary),
                        AlphaColor(android.R.attr.textColorSecondary, 128),
                        AlphaColor(android.R.attr.textColorSecondaryInverse),
                        AlphaColor(android.R.attr.textColorSecondaryInverse, 128)
                ))
                .appendln("secondary_inverse_selected_accent".withBlocks(
                        AlphaColor(android.R.attr.textColorSecondaryInverse),
                        AlphaColor(android.R.attr.textColorSecondaryInverse, 128),
                        AlphaColor(R.attr.colorAccent),
                        AlphaColor(R.attr.colorAccent, 128)
                ))
                .appendln("secondary_inverse_selected_inverse".withBlocks(
                        AlphaColor(android.R.attr.textColorSecondaryInverse),
                        AlphaColor(android.R.attr.textColorSecondaryInverse, 128),
                        AlphaColor(android.R.attr.textColorSecondary),
                        AlphaColor(android.R.attr.textColorSecondary, 128)
                ))
                .appendln("tertiary_selected_accent".withBlocks(
                        AlphaColor(android.R.attr.textColorTertiary),
                        AlphaColor(android.R.attr.textColorTertiary, 128),
                        AlphaColor(R.attr.colorAccent),
                        AlphaColor(R.attr.colorAccent, 128)
                ))
                .appendln("tertiary_selected_inverse".withBlocks(
                        AlphaColor(android.R.attr.textColorTertiary),
                        AlphaColor(android.R.attr.textColorTertiary, 128),
                        AlphaColor(android.R.attr.textColorTertiaryInverse),
                        AlphaColor(android.R.attr.textColorTertiaryInverse, 128)
                ))
                .appendln("tertiary_inverse_selected_accent".withBlocks(
                        AlphaColor(android.R.attr.textColorTertiaryInverse),
                        AlphaColor(android.R.attr.textColorTertiaryInverse, 128),
                        AlphaColor(R.attr.colorAccent),
                        AlphaColor(R.attr.colorAccent, 128)
                ))
                .appendln("tertiary_inverse_selected_inverse".withBlocks(
                        AlphaColor(android.R.attr.textColorTertiaryInverse),
                        AlphaColor(android.R.attr.textColorTertiaryInverse, 128),
                        AlphaColor(android.R.attr.textColorTertiary),
                        AlphaColor(android.R.attr.textColorTertiary, 128)
                ))
                .appendln().appendln("REGULAR SELECTORS (with correct disabled state)")
                .appendln().appendln("color blocks order:")
                .appendln("1. regular")
                .appendln("2. disabled")
                .appendln()
                .appendln("accent_disabled_default".withBlocks(
                        AlphaColor(R.attr.colorAccent),
                        AlphaColor(R.attr.textColorDefaultDisabled, 128)
                ))
                .appendln("accent_disabled_natural".withBlocks(
                        AlphaColor(R.attr.colorAccent),
                        AlphaColor(R.attr.colorAccent, 128)
                ))
                .appendln("error_disabled_default".withBlocks(
                        AlphaColor(R.attr.textColorError),
                        AlphaColor(R.attr.textColorDefaultDisabled, 128)
                ))
                .appendln("error_disabled_natural".withBlocks(
                        AlphaColor(R.attr.textColorError),
                        AlphaColor(R.attr.textColorError, 128)
                ))
                .appendln("primary_dark_disabled_default".withBlocks(
                        AlphaColor(R.attr.colorPrimaryDark),
                        AlphaColor(R.attr.textColorDefaultDisabled, 128)
                ))
                .appendln("primary_dark_disabled_natural".withBlocks(
                        AlphaColor(R.attr.colorPrimaryDark),
                        AlphaColor(R.attr.colorPrimaryDark, 128)
                ))
                .appendln("primary_disabled_default".withBlocks(
                        AlphaColor(R.attr.colorPrimary),
                        AlphaColor(R.attr.textColorDefaultDisabled, 128)
                ))
                .appendln("primary_disabled_natural".withBlocks(
                        AlphaColor(R.attr.colorPrimary),
                        AlphaColor(R.attr.colorPrimary, 128)
                ))
                .appendln("primary_light_disabled_default".withBlocks(
                        AlphaColor(R.attr.colorPrimaryLight),
                        AlphaColor(R.attr.textColorDefaultDisabled, 128)
                ))
                .appendln("primary_light_disabled_natural".withBlocks(
                        AlphaColor(R.attr.colorPrimaryLight),
                        AlphaColor(R.attr.colorPrimaryLight, 128)
                ))
                .appendln("secondary_disabled_default".withBlocks(
                        AlphaColor(android.R.attr.textColorSecondary),
                        AlphaColor(R.attr.textColorDefaultDisabled, 128)
                ))
                .appendln("secondary_disabled_natural".withBlocks(
                        AlphaColor(android.R.attr.textColorSecondary),
                        AlphaColor(android.R.attr.textColorSecondary, 128)
                ))
                .appendln("secondary_inverse_disabled_default".withBlocks(
                        AlphaColor(android.R.attr.textColorSecondaryInverse),
                        AlphaColor(R.attr.textColorDefaultDisabled, 128)
                ))
                .appendln("secondary_inverse_disabled_natural".withBlocks(
                        AlphaColor(android.R.attr.textColorSecondaryInverse),
                        AlphaColor(android.R.attr.textColorSecondaryInverse, 128)
                ))
                .appendln("tertiary_disabled_default".withBlocks(
                        AlphaColor(android.R.attr.textColorTertiaryInverse),
                        AlphaColor(R.attr.textColorDefaultDisabled, 128)
                ))
                .appendln("tertiary_disabled_natural".withBlocks(
                        AlphaColor(android.R.attr.textColorTertiaryInverse),
                        AlphaColor(android.R.attr.textColorTertiaryInverse, 128)
                ))
                .appendln("tertiary_inverse_disabled_default".withBlocks(
                        AlphaColor(android.R.attr.textColorTertiaryInverse),
                        AlphaColor(R.attr.textColorDefaultDisabled, 128)
                ))
                .appendln("tertiary_inverse_disabled_natural".withBlocks(
                        AlphaColor(android.R.attr.textColorTertiaryInverse),
                        AlphaColor(android.R.attr.textColorTertiaryInverse, 128)
                ))
                .let { it as SpannableStringBuilder }
                .let(colorsLabel::setText)
    }

    companion object {
        fun newInstance() = SelectorOverviewFragment()
    }
}