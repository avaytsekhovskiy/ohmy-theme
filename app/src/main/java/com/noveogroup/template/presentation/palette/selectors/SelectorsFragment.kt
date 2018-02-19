package com.noveogroup.template.presentation.palette.selectors

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
import com.noveogroup.template.presentation.common.android.BaseFragment
import com.noveogroup.template.presentation.common.android.inflater.Layout
import com.noveogroup.template.presentation.di.DI
import kotlinx.android.synthetic.main.fragment_selectors.*

@Layout(R.layout.fragment_selectors)
class SelectorsFragment : BaseFragment(), SelectorsView {

    @InjectPresenter
    lateinit var presenter: SelectorsPresenter

    @ProvidePresenter
    fun providePresenter() = DI.paletteScope.getInstance(SelectorsPresenter::class.java)!!

    private val uiControls by lazy {
        listOf(
                radioDdFirst, radioDdSecond,
                radioDnFirst, radioDnSecond,
                radioSaFirst, radioSaSecond,
                radioSiFirst, radioSiSecond
        )
    }

    override fun enableViews() = changeViewsEnabled(true)

    override fun disableViews() = changeViewsEnabled(false)

    private fun changeViewsEnabled(enabled: Boolean) = uiControls.forEach { it.isEnabled = enabled }

    override fun showExplanation() {
        dialogDelegate.showSimpleDialog(
                title = "Theme attrs",
                description = """
                               |ohmyCs_COLOR_BEHAVIOR attrs
                                """.trimMargin()
        )
    }

    companion object {
        fun newInstance() = SelectorsFragment()
    }
}
