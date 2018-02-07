package com.noveogroup.template.presentation.palette.toolbar

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.ScreenStateDiffHelper
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import com.noveogroup.template.presentation.common.mvp.ScreenStateListener
import javax.inject.Inject

@InjectViewState
class ToolbarPresenter @Inject constructor(
        globalRouter: GlobalRouter,
        statePublisher: ScreenInteractor
) : BasePresenter<ToolbarView>(globalRouter, statePublisher), ScreenStateListener {

    override fun onScreenStateChanged(helper: ScreenStateDiffHelper) = with(helper) {
        ifTitleChanged(viewState::changeTitle)
    }

    override fun back() = globalRouter.exit()

}
