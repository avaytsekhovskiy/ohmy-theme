package com.noveogroup.template.presentation.main.part.toolbar

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.core.ext.warnOrThrow
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.ScreenStateDiffHelper
import com.noveogroup.template.domain.interactor.state.model.ScreenState
import com.noveogroup.template.domain.interactor.state.model.Toggle
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.domain.navigation.router.MainRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import com.noveogroup.template.presentation.common.mvp.ScreenStateListener
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@InjectViewState
class ToolbarPresenter @Inject constructor(
        private val mainRouter: MainRouter,
        override val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<ToolbarView>(globalRouter), ScreenStateListener {

    override var previousState: ScreenState? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        screenInteractor
                .listenForChanges(AndroidSchedulers.mainThread(), ::dispatchAppearance)
                .unsubscribeOnDestroy()
    }

    override fun onScreenStateChanged(helper: ScreenStateDiffHelper) = with(helper) {
        ifPageModeChanged(viewState::changePageMode)
        ifTitleChanged(viewState::changeTitle)
        ifToolbarMenuChanged(viewState::changeToolbarMenu)
        ifToggleChanged(viewState::changeToggle)
    }

    override fun back() = mainRouter.exit()

    fun handleMenuItemClick(menuItem: MenuItemDescriptor) {
        viewState.showDebugMessage(menuItem.name)
    }

    fun handleActionBarToggle() = when (screenInteractor.state.toggle) {
        Toggle.BURGER -> toggleMenu()
        Toggle.BACK -> back()
        else -> log.warnOrThrow("unknown LeftToggle state")
    }

    private fun toggleMenu() {
        screenInteractor.publish(sideMode = screenInteractor.state.sideMode.toggle())
    }

}
