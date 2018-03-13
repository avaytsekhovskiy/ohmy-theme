package com.noveogroup.template.presentation.main.page.selector_overview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.text.buildSpannedString
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
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
        resolveThemeAttrs().let(colorsLabel::setText)
    }

    private fun resolveThemeAttrs() = buildSpannedString {
        appendln("NAME: ohmy_cs_*")
        appendln()
        appendln("SELECTABLE SELECTORS")
        appendln()
        appendln("color blocks order:")
        appendln("1. regular")
        appendln("2. regular disabled")
        appendln("3. selected")
        appendln("4. selected disabled")
        appendln()
        appendln("primary_selected_accent".selectableSelectorOf(
                R.attr.colorPrimary,
                R.attr.colorAccent
        ))
        appendln("primary_dark_selected_accent".selectableSelectorOf(
                R.attr.colorPrimaryDark,
                R.attr.colorAccent
        ))
        appendln("primary_dark_selected_inverse".selectableSelectorOf(
                R.attr.colorPrimaryDark,
                R.attr.colorPrimaryLight
        ))
        appendln("primary_light_selected_accent".selectableSelectorOf(
                R.attr.colorPrimaryLight,
                R.attr.colorAccent
        ))
        appendln("primary_light_selected_inverse".selectableSelectorOf(
                R.attr.colorPrimaryLight,
                R.attr.colorPrimaryDark
        ))
        appendln("secondary_selected_accent".selectableSelectorOf(
                android.R.attr.textColorSecondary,
                R.attr.colorAccent
        ))
        appendln("secondary_selected_inverse".selectableSelectorOf(
                android.R.attr.textColorSecondary,
                android.R.attr.textColorSecondaryInverse
        ))
        appendln("secondary_inverse_selected_accent".selectableSelectorOf(
                android.R.attr.textColorSecondaryInverse,
                R.attr.colorAccent
        ))
        appendln("secondary_inverse_selected_inverse".selectableSelectorOf(
                android.R.attr.textColorSecondaryInverse,
                android.R.attr.textColorSecondary
        ))
        appendln("tertiary_selected_accent".selectableSelectorOf(
                android.R.attr.textColorTertiary,
                R.attr.colorAccent
        ))
        appendln("tertiary_selected_inverse".selectableSelectorOf(
                android.R.attr.textColorTertiary,
                android.R.attr.textColorTertiaryInverse
        ))
        appendln("tertiary_inverse_selected_accent".selectableSelectorOf(
                android.R.attr.textColorTertiaryInverse,
                R.attr.colorAccent
        ))
        appendln("tertiary_inverse_selected_inverse".selectableSelectorOf(
                android.R.attr.textColorTertiaryInverse,
                android.R.attr.textColorTertiary
        ))
        appendln()
        appendln("REGULAR SELECTORS (with correct disabled state)")
        appendln()
        appendln("color blocks order:")
        appendln("1. regular")
        appendln("2. disabled")
        appendln()
        appendln("accent_disabled_default".selectorOf(
                R.attr.colorAccent,
                R.attr.textColorDefaultDisabled
        ))
        appendln("accent_disabled_natural".selectorOf(R.attr.colorAccent))
        appendln("error_disabled_default".selectorOf(
                R.attr.textColorError,
                R.attr.textColorDefaultDisabled
        ))
        appendln("error_disabled_natural".selectorOf(R.attr.textColorError))
        appendln("primary_dark_disabled_default".selectorOf(
                R.attr.colorPrimaryDark,
                R.attr.textColorDefaultDisabled
        ))
        appendln("primary_dark_disabled_natural".selectorOf(R.attr.colorPrimaryDark))
        appendln("primary_disabled_default".selectorOf(
                R.attr.colorPrimary,
                R.attr.textColorDefaultDisabled
        ))
        appendln("primary_disabled_natural".selectorOf(R.attr.colorPrimary))
        appendln("primary_light_disabled_default".selectorOf(
                R.attr.colorPrimaryLight,
                R.attr.textColorDefaultDisabled
        ))
        appendln("primary_light_disabled_natural".selectorOf(R.attr.colorPrimaryLight))
        appendln("secondary_disabled_default".selectorOf(
                android.R.attr.textColorSecondary,
                R.attr.textColorDefaultDisabled
        ))
        appendln("secondary_disabled_natural".selectorOf(android.R.attr.textColorSecondary))
        appendln("secondary_inverse_disabled_default".selectorOf(
                android.R.attr.textColorSecondaryInverse,
                R.attr.textColorDefaultDisabled
        ))
        appendln("secondary_inverse_disabled_natural".selectorOf(android.R.attr.textColorSecondaryInverse))
        appendln("tertiary_disabled_default".selectorOf(
                android.R.attr.textColorTertiaryInverse,
                R.attr.textColorDefaultDisabled
        ))
        appendln("tertiary_disabled_natural".selectorOf(android.R.attr.textColorTertiaryInverse))
        appendln("tertiary_inverse_disabled_default".selectorOf(
                android.R.attr.textColorTertiaryInverse,
                R.attr.textColorDefaultDisabled
        ))
        appendln("tertiary_inverse_disabled_natural".selectorOf(android.R.attr.textColorTertiaryInverse))
    }

    companion object {
        fun newInstance() = SelectorOverviewFragment()
    }
}