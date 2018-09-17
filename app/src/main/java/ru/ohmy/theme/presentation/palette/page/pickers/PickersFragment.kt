package ru.ohmy.theme.presentation.palette.page.pickers

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.ohmy.theme.R
import ru.ohmy.theme.presentation.common.android.PagerFragment
import ru.ohmy.theme.presentation.common.android.inflater.Layout
import ru.ohmy.theme.presentation.di.DI
import kotlinx.android.synthetic.main.fragment_bars.*

@Layout(R.layout.fragment_bars)
class PickersFragment : PagerFragment(), PickersView {

    @InjectPresenter
    lateinit var presenter: PickersPresenter

    @ProvidePresenter
    fun providePresenter() = DI.paletteScope.getInstance(PickersPresenter::class.java)!!

    private val uiControls by lazy {
        listOf(
                ratingBar, seekBar,
                spinnerPicker, calendarPicker
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun enableViews() = changeViewsDisabled(true)

    override fun disableViews() = changeViewsDisabled(false)

    private fun changeViewsDisabled(enabled: Boolean) {
        uiControls.forEach { it.isEnabled = enabled }
    }

    override fun showExplanation() {
        dialogDelegate.showSimpleDialog(
                title = "Theme attrs",
                description = """
                               |How default colors used by complex views
                                """.trimMargin()
        )
    }

    companion object {
        fun newInstance() = PickersFragment()
    }
}
