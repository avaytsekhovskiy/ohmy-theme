package ru.ohmy.theme.presentation.main.page.welcome

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.ohmy.theme.R
import ru.ohmy.theme.presentation.common.android.BaseFragment
import ru.ohmy.theme.presentation.common.android.inflater.Layout
import ru.ohmy.theme.presentation.di.DI
import kotlinx.android.synthetic.main.fragment_welcome.*

@Layout(R.layout.fragment_welcome)
class WelcomeFragment : BaseFragment() {

    @InjectPresenter
    lateinit var presenter: WelcomePresenter

    @ProvidePresenter
    fun providePresenter() = DI.mainScope.getInstance(WelcomePresenter::class.java)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inheritanceButton.setOnClickListener { presenter.openInheritance() }
        themeOverviewButton.setOnClickListener { presenter.openThemeOverview() }
        selectorOverviewButton.setOnClickListener { presenter.openSelectorOverview() }
        paletteButton.setOnClickListener { presenter.openPalette() }
    }

    companion object {
        fun newInstance() = WelcomeFragment()
    }
}
