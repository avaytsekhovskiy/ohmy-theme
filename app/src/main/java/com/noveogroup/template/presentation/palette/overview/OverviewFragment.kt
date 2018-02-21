package com.noveogroup.template.presentation.palette.overview

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.AttrRes
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
import kotlinx.android.synthetic.main.fragment_theme_overview.*

@Suppress("UsePropertyAccessSyntax")
@SuppressLint("SetTextI18n")
@Layout(R.layout.fragment_theme_overview)
class OverviewFragment : BaseFragment(), OverviewView {

    @InjectPresenter
    lateinit var presenter: OverviewPresenter

    @ProvidePresenter
    fun providePresenter() = DI.paletteScope.getInstance(OverviewPresenter::class.java)!!

    val resourceManager by lazy { ResourceManager(baseActivity) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        root.setBackgroundColor(colorOf(android.R.attr.listDivider))
        SpannableStringBuilder()
                .append("BACKGROUNDS\n")
                .append(span(android.R.attr.colorBackground, "android:colorBackground")).append("\n")
                .append(span(android.R.attr.colorForeground, "android:colorForeground")).append("\n")
                .append("\nCOLORS BRIGHT\n")
                .append(span(R.attr.colorPrimaryLight, "colorPrimaryLight")).append("\n")
                .append(span(R.attr.colorPrimary, "colorPrimary")).append("\n")
                .append(span(R.attr.colorPrimaryDark, "colorPrimaryDark")).append("\n")
                .append(span(R.attr.colorAccent, "colorAccent")).append("\n")
                .append("\nCOLORS TEXT\n")
                .append(span(R.attr.textColorError, "textColorError")).append("\n")
                .append(span(android.R.attr.textColorPrimary, "android:textColorPrimary = colorPrimary")).append("\n")
                .append(span(android.R.attr.textColorSecondary, "android:textColorSecondary")).append("\n")
                .append(span(android.R.attr.textColorSecondaryInverse, "android:textColorSecondaryInverse")).append("\n")
                .append(span(android.R.attr.textColorTertiary, "android:textColorTertiary")).append("\n")
                .append(span(android.R.attr.textColorTertiaryInverse, "android:textColorTertiaryInverse")).append("\n")
                .append("\nSELECTORS\n")
                .append("android:textColor = ").append(span(android.R.attr.textColorSecondary, "secondaryDisabledNatural")).append("\n")
                .append("android:editTextColor = ").append(span(android.R.attr.textColorSecondary, "secondaryDisabledNatural")).append("\n")
                .append("android:textColorPrimaryDisabledOnly = ").append(span(android.R.attr.textColorSecondary, "secondaryDisabledNatural")).append("\n")
                .append("android:textColorHint = ").append(span(android.R.attr.textColorTertiary, "tertiaryDisabledNatural")).append("\n")
                .append("android:textColorLink = ").append(span(R.attr.colorAccent, "accentDisabledNatural")).append("\n")
                .let(colorsLabel::setText)
    }

    private fun span(@AttrRes attrId: Int, text: CharSequence): Spannable = SpannableString(text).apply {
        setSpan(ForegroundColorSpan(colorOf(attrId)), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private fun colorOf(@AttrRes attrId: Int) = resourceManager.resolve(baseActivity, attrId)

    companion object {
        fun newInstance() = OverviewFragment()
    }
}