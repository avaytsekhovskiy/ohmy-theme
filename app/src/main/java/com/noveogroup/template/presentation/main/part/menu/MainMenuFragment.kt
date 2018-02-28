package com.noveogroup.template.presentation.main.part.menu

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
import com.noveogroup.template.presentation.common.android.inflater.Layout
import com.noveogroup.template.presentation.common.android.menu.AppMenuFragment
import com.noveogroup.template.presentation.common.android.menu.AppMenuPresenter
import com.noveogroup.template.presentation.di.DI


@Layout(R.layout.fragment_app_menu)
class MainMenuFragment : AppMenuFragment() {

    @InjectPresenter
    override lateinit var presenter: AppMenuPresenter

    @ProvidePresenter
    fun providePresenter() = DI.mainScope.getInstance(AppMenuPresenter::class.java)!!

}

