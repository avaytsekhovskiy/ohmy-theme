package com.noveogroup.template.presentation.main.page.welcome

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.R
import com.noveogroup.template.data.android.system.ResourceManager
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.model.PageMode
import com.noveogroup.template.domain.interactor.state.model.SideMode
import com.noveogroup.template.domain.interactor.state.model.Toggle
import com.noveogroup.template.domain.interactor.state.model.ToolbarMenu
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.domain.navigation.router.MainRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import com.noveogroup.template.presentation.di.module.main.MainActivityName
import javax.inject.Inject

@InjectViewState
class WelcomePresenter @Inject constructor(
        @MainActivityName private val mainActivityName: String,
        private val mainRouter: MainRouter,
        private val resourceManager: ResourceManager,
        globalRouter: GlobalRouter,
        statePublisher: ScreenInteractor
) : BasePresenter<WelcomeView>(globalRouter, statePublisher) {

    override fun attachView(view: WelcomeView) {
        super.attachView(view)
        viewState.showName(mainActivityName)
        requestAppearance(
                pageMode = PageMode.TOOLBAR,
                toggle = Toggle.BURGER,
                sideMode = SideMode.CLOSED,
                toolbarMenu = ToolbarMenu.WELCOME,
                title = resourceManager.getString(R.string.title_welcome))
    }

    internal fun openNext() = mainRouter.openSecond()
}
