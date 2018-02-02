package com.noveogroup.template.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.domain.navigation.router.MainRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
        private val mainRouter: MainRouter,
        globalRouter: GlobalRouter,
        statePublisher: ScreenInteractor
) : BasePresenter<MainView>(globalRouter, statePublisher) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        mainRouter.startFromWelcome()
    }

    override fun back() = mainRouter.exit()

}
