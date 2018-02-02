package com.noveogroup.template.presentation.main.part.menu

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.ScreenStateDiffHelper
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.domain.navigation.router.MainRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import com.noveogroup.template.presentation.common.mvp.ScreenStateListener
import javax.inject.Inject

@InjectViewState
class LeftMenuPresenter @Inject constructor(
        @Suppress("unused") private val mainRouter: MainRouter,
        globalRouter: GlobalRouter,
        statePublisher: ScreenInteractor
) : BasePresenter<LeftMenuView>(globalRouter, statePublisher), ScreenStateListener {

    override fun onScreenStateChanged(helper: ScreenStateDiffHelper) = helper.ifSideModeChanged { menu ->
        viewState.apply {
            when {
                menu.locked && menu.opened -> lockOpened()
                menu.locked -> lockClosed()
                menu.opened -> open().also { unlock() }
                else -> close().also { unlock() }
            }
        }
    }

    fun openSomething(something: String) {
        closeMenu()
        //open something with mainRouter.
        viewState.showDebugMessage("openSomething :: $something")
    }

    fun openMenu() = toggle(true)
            .also { log.debug("on menu opened") }

    fun closeMenu() = toggle(false)
            .also { log.debug("on menu closed") }

    private fun toggle(opened: Boolean) {
        requestAppearance(sideMode = stateInteractor.state.sideMode.toggle(opened))
    }
}
