package com.noveogroup.template.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.data.android.system.ResourceManager
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.model.PageMode
import com.noveogroup.template.domain.interactor.state.model.ToolbarMenu
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.domain.navigation.router.MainRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import com.noveogroup.template.presentation.common.mvp.view.BaseView
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
        private val resourceManager: ResourceManager,
        private val mainRouter: MainRouter,
        private val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<BaseView>(globalRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        mainRouter.startFromWelcome()
    }

    override fun attachView(view: BaseView?) {
        super.attachView(view)
        screenInteractor.publish(
                toolbarMenu = ToolbarMenu.NO_MENU,
                pageMode = PageMode.TOOLBAR
        )
    }

    override fun back() = mainRouter.exit()

}
