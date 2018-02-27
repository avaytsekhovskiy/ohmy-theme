package com.noveogroup.template.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.debugdrawer.data.theme.ThemeProxy
import com.noveogroup.preferences.guava.Optional
import com.noveogroup.preferences.lambda.Consumer
import com.noveogroup.template.data.android.system.ResourceManager
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.model.PageMode
import com.noveogroup.template.domain.interactor.state.model.ToolbarMenu
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.domain.navigation.router.MainRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
        private val themeProxy: ThemeProxy,
        private val resourceManager: ResourceManager,
        private val mainRouter: MainRouter,
        private val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<MainView>(globalRouter) {

    private val themeChangedListener = Consumer<Optional<Int>> {
        viewState.hideSettings()
        viewState.recreate()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        mainRouter.startFromWelcome()
        themeProxy.addListener(themeChangedListener)
    }

    override fun attachView(view: MainView?) {
        super.attachView(view)
        screenInteractor.publish(
                toolbarMenu = ToolbarMenu.NO_MENU,
                pageMode = PageMode.TOOLBAR
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        themeProxy.removeListener(themeChangedListener)
    }

    override fun back() = mainRouter.exit()

}
