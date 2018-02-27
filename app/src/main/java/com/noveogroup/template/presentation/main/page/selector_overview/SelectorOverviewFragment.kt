package com.noveogroup.template.presentation.main.page.selector_overview

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
class SelectorOverviewFragment : BaseFragment(), SelectorOverviewView {

    @InjectPresenter
    lateinit var presenter: SelectorOverviewPresenter

    @ProvidePresenter
    fun providePresenter() = DI.mainScope.getInstance(SelectorOverviewPresenter::class.java)!!

    val resourceManager by lazy { ResourceManager(baseActivity) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SpannableStringBuilder()
                .appendln("SCHEME")
                .appendln("1. active")
                .appendln("2. active disabled")
                .appendln("3. regular")
                .appendln("4. regular disabled")
                .appendln().appendln("ACTIVE SELECTORS")
                .append(span(R.attr.colorPrimary, "█"))
                .append(spanAlpha(128, R.attr.colorPrimary, "█"))
                .append(span(R.attr.colorAccent, "█"))
                .append(spanAlpha(128, R.attr.colorAccent, "█")).append(' ')
                .appendln("ohmy_cs_primary_selected_accent")
                .append(span(R.attr.colorPrimaryDark, "█"))
                .append(spanAlpha(128, R.attr.colorPrimaryDark, "█"))
                .append(span(R.attr.colorAccent, "█"))
                .append(spanAlpha(128, R.attr.colorAccent, "█")).append(' ')
                .appendln("ohmy_cs_primary_dark_selected_accent")
                .append(span(R.attr.colorPrimaryDark, "█"))
                .append(spanAlpha(128, R.attr.colorPrimaryDark, "█"))
                .append(span(R.attr.colorPrimaryLight, "█"))
                .append(spanAlpha(128, R.attr.colorPrimaryLight, "█")).append(' ')
                .appendln("ohmy_cs_primary_dark_selected_inverse")
                .append(span(R.attr.colorPrimaryLight, "█"))
                .append(spanAlpha(128, R.attr.colorPrimaryLight, "█"))
                .append(span(R.attr.colorAccent, "█"))
                .append(spanAlpha(128, R.attr.colorAccent, "█")).append(' ')
                .appendln("ohmy_cs_primary_light_selected_accent")
                .append(span(R.attr.colorPrimaryLight, "█"))
                .append(spanAlpha(128, R.attr.colorPrimaryLight, "█"))
                .append(span(R.attr.colorPrimaryDark, "█"))
                .append(spanAlpha(128, R.attr.colorPrimaryDark, "█")).append(' ')
                .appendln("ohmy_cs_primary_light_selected_inverse")
                .append(span(android.R.attr.textColorSecondary, "█"))
                .append(spanAlpha(128, android.R.attr.textColorSecondary, "█"))
                .append(span(R.attr.colorAccent, "█"))
                .append(spanAlpha(128, R.attr.colorAccent, "█")).append(' ')
                .appendln("ohmy_cs_secondary_selected_accent")
                .append(span(android.R.attr.textColorSecondary, "█"))
                .append(spanAlpha(128, android.R.attr.textColorSecondary, "█"))
                .append(span(android.R.attr.textColorSecondaryInverse, "█"))
                .append(spanAlpha(128, android.R.attr.textColorSecondaryInverse, "█")).append(' ')
                .appendln("ohmy_cs_secondary_selected_inverse")
                .append(span(android.R.attr.textColorSecondaryInverse, "█"))
                .append(spanAlpha(128, android.R.attr.textColorSecondaryInverse, "█"))
                .append(span(R.attr.colorAccent, "█"))
                .append(spanAlpha(128, R.attr.colorAccent, "█")).append(' ')
                .appendln("ohmy_cs_secondary_inverse_selected_accent")
                .append(span(android.R.attr.textColorSecondaryInverse, "█"))
                .append(spanAlpha(128, android.R.attr.textColorSecondaryInverse, "█"))
                .append(span(android.R.attr.textColorSecondary, "█"))
                .append(spanAlpha(128, android.R.attr.textColorSecondary, "█")).append(' ')
                .appendln("ohmy_cs_secondary_inverse_selected_inverse")
                .append(span(android.R.attr.textColorTertiary, "█"))
                .append(spanAlpha(128, android.R.attr.textColorTertiary, "█"))
                .append(span(R.attr.colorAccent, "█"))
                .append(spanAlpha(128, R.attr.colorAccent, "█")).append(' ')
                .appendln("ohmy_cs_tertiary_selected_accent")
                .append(span(android.R.attr.textColorTertiary, "█"))
                .append(spanAlpha(128, android.R.attr.textColorTertiary, "█"))
                .append(span(android.R.attr.textColorTertiaryInverse, "█"))
                .append(spanAlpha(128, android.R.attr.textColorTertiaryInverse, "█")).append(' ')
                .appendln("ohmy_cs_tertiary_selected_inverse")
                .append(span(android.R.attr.textColorTertiaryInverse, "█"))
                .append(spanAlpha(128, android.R.attr.textColorTertiaryInverse, "█"))
                .append(span(R.attr.colorAccent, "█"))
                .append(spanAlpha(128, R.attr.colorAccent, "█")).append(' ')
                .appendln("ohmy_cs_tertiary_inverse_selected_accent")
                .append(span(android.R.attr.textColorTertiaryInverse, "█"))
                .append(spanAlpha(128, android.R.attr.textColorTertiaryInverse, "█"))
                .append(span(android.R.attr.textColorTertiary, "█"))
                .append(spanAlpha(128, android.R.attr.textColorTertiary, "█")).append(' ')
                .appendln("ohmy_cs_tertiary_inverse_selected_inverse")
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
        fun newInstance() = SelectorOverviewFragment()
    }
}