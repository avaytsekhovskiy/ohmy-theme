package com.noveogroup.template.presentation.splash

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import com.noveogroup.template.presentation.common.mvp.view.BaseView
import javax.inject.Inject

@InjectViewState
class SplashPresenter @Inject constructor(
        globalRouter: GlobalRouter,
        statePublisher: ScreenInteractor
) : BasePresenter<BaseView>(globalRouter, statePublisher) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        globalRouter.startFromMain()
    }

}
