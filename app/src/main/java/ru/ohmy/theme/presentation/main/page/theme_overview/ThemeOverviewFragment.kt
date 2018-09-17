package ru.ohmy.theme.presentation.main.page.theme_overview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.text.buildSpannedString
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.ohmy.theme.R
import ru.ohmy.theme.data.android.system.ResourceManager
import ru.ohmy.theme.presentation.common.android.BaseFragment
import ru.ohmy.theme.presentation.common.android.inflater.Layout
import ru.ohmy.theme.presentation.di.DI
import ru.ohmy.theme.presentation.main.page.OverviewFragment
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
        resolveThemeAttrs().let(colorsLabel::setText)
    }

    private fun resolveThemeAttrs() = buildSpannedString {
        appendln("BACKGROUNDS")
        appendln("android:colorBackground".colorOf(android.R.attr.colorBackground))
        appendln("android:colorForeground".colorOf(android.R.attr.colorForeground))
        appendln()
        appendln("COLORS BRIGHT")
        appendln("colorPrimaryLight".colorOf(R.attr.colorPrimaryLight))
        appendln("colorPrimary".colorOf(R.attr.colorPrimary))
        appendln("colorPrimaryDark".colorOf(R.attr.colorPrimaryDark))
        appendln("colorAccent".colorOf(R.attr.colorAccent))
        appendln()
        appendln("COLORS TEXT")
        appendln("textColorError".colorOf(R.attr.textColorError))
        appendln("android:textColorPrimary = colorPrimary".colorOf(android.R.attr.textColorPrimary))
        appendln("android:textColorSecondary".colorOf(android.R.attr.textColorSecondary))
        appendln("android:textColorSecondaryInverse".colorOf(android.R.attr.textColorSecondaryInverse))
        appendln("android:textColorTertiary".colorOf(android.R.attr.textColorTertiary))
        appendln("android:textColorTertiaryInverse".colorOf(android.R.attr.textColorTertiaryInverse))
        appendln()
        appendln("SELECTORS")
        appendln("android:textColor = secondaryDisabledNatural".selectorOf(android.R.attr.textColorSecondary))
        appendln("android:editTextColor = secondaryDisabledNatural".selectorOf(android.R.attr.textColorSecondary))
        appendln("android:textColorHint = tertiaryDisabledNatural".selectorOf(android.R.attr.textColorTertiary))
        appendln("android:textColorLink = accentDisabledNatural".selectorOf(R.attr.colorAccent))
    }

    companion object {
        fun newInstance() = ThemeOverviewFragment()
    }
}