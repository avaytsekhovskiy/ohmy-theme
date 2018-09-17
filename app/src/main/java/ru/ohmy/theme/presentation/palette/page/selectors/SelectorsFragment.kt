package ru.ohmy.theme.presentation.palette.page.selectors

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.ohmy.theme.R
import ru.ohmy.theme.presentation.common.android.PagerFragment
import ru.ohmy.theme.presentation.common.android.inflater.Layout
import ru.ohmy.theme.presentation.di.DI
import kotlinx.android.synthetic.main.fragment_selectors.*

@Layout(R.layout.fragment_selectors)
class SelectorsFragment : PagerFragment(), SelectorsView {

    @InjectPresenter
    lateinit var presenter: SelectorsPresenter

    @ProvidePresenter
    fun providePresenter() = DI.paletteScope.getInstance(SelectorsPresenter::class.java)!!

    private val uiControls by lazy {
        listOf(
                radioDdFirst, radioDdSecond,
                radioDnFirst, radioDnSecond,
                radioSaFirst, radioSaSecond,
                radioSiFirst, radioSiSecond,
                checkDdFirst, checkDdSecond,
                checkDnFirst, checkDnSecond,
                checkSaFirst, checkSaSecond,
                checkSiFirst, checkSiSecond
        )
    }

    override fun enableViews() = changeViewsEnabled(true)

    override fun disableViews() = changeViewsEnabled(false)

    private fun changeViewsEnabled(enabled: Boolean) = uiControls.forEach { it.isEnabled = enabled }

    override fun showExplanation() {
        dialogDelegate.showSimpleDialog(
                title = "Theme attrs",
                description = """
                               |R.attr.ohmyCs* / ?ohmyCs*
                               |  - "cs" is xml color selector
                               |
                               |regular views require 2 states:
                               |  - normal
                               |  - disabled
                               |use as textColor:
                               |  ohmyCs*Disabled(Default/Natural)
                               |
                               |active views require 4 states:
                               |  - active
                               |  - active disabled
                               |  - normal
                               |  - disabled
                               |use as buttonTint:
                               |  ohmyCs*Selected(Accent/Inverse)
                               |
                               |__________________________________
                               |
                               |see more in R.layout.fragment_selectors
                                """.trimMargin()
        )
    }

    companion object {
        fun newInstance() = SelectorsFragment()
    }
}
