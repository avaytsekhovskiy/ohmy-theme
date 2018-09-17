package ru.ohmy.theme.presentation.palette.part.menu

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.ohmy.theme.R
import ru.ohmy.theme.presentation.common.android.inflater.Layout
import ru.ohmy.theme.presentation.common.android.menu.AppMenuFragment
import ru.ohmy.theme.presentation.common.android.menu.AppMenuPresenter
import ru.ohmy.theme.presentation.di.DI


@Layout(R.layout.fragment_app_menu)
class PaletteMenuFragment : AppMenuFragment() {

    @InjectPresenter
    override lateinit var presenter: AppMenuPresenter

    @ProvidePresenter
    fun providePresenter() = DI.paletteScope.getInstance(AppMenuPresenter::class.java)!!

}

