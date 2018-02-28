package com.noveogroup.template.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.core.ext.observeSafe
import com.noveogroup.template.domain.interactor.ThemeInteractor
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.model.PageMode
import com.noveogroup.template.domain.interactor.state.model.ToolbarMenu
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.domain.navigation.router.MainRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
        private val themeInteractor: ThemeInteractor,
        private val mainRouter: MainRouter,
        private val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<MainView>(globalRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        mainRouter.startFromWelcome()

        themeInteractor.observeSettings()
                .observeSafe(AndroidSchedulers.mainThread()) { viewState.recreate() }
                .unsubscribeOnDestroy()
    }

    override fun attachView(view: MainView?) {
        super.attachView(view)

        screenInteractor.publish(
                toolbarMenu = ToolbarMenu.MAIN_MENU,
                pageMode = PageMode.TOOLBAR
        )
    }

    override fun back() = mainRouter.exit()

}
