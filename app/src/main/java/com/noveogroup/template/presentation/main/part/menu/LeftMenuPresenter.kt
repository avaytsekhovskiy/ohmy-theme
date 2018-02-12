package com.noveogroup.template.presentation.main.part.menu

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.ScreenStateDiffHelper
import com.noveogroup.template.domain.interactor.state.model.ScreenState
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.domain.navigation.router.MainRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import com.noveogroup.template.presentation.common.mvp.ScreenStateListener
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@InjectViewState
class LeftMenuPresenter @Inject constructor(
        @Suppress("unused") private val mainRouter: MainRouter,
        override val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<LeftMenuView>(globalRouter), ScreenStateListener {

    override var previousState: ScreenState? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        screenInteractor
                .listenForChanges(AndroidSchedulers.mainThread(), ::dispatchAppearance)
                .unsubscribeOnDestroy()
    }

    override fun onScreenStateChanged(helper: ScreenStateDiffHelper) = with(viewState) {
        helper.ifSideModeChanged { aside ->
            when {
                aside.locked && aside.opened -> lockOpened()
                aside.locked -> lockClosed()
                aside.opened -> open().also { unlock() }
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
        screenInteractor.publish(sideMode = screenInteractor.state.sideMode.toggle(opened))
    }
}
