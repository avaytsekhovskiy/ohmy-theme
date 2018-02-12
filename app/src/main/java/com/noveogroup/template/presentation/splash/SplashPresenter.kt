package com.noveogroup.template.presentation.splash

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import com.noveogroup.template.presentation.common.mvp.view.BaseView
import javax.inject.Inject

@InjectViewState
class SplashPresenter @Inject constructor(
        globalRouter: GlobalRouter
) : BasePresenter<BaseView>(globalRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        globalRouter.startFromMain()
    }
}
