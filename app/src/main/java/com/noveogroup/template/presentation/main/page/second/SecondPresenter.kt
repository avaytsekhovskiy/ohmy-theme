package com.noveogroup.template.presentation.main.page.second

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
import com.noveogroup.template.presentation.common.mvp.view.BaseView
import javax.inject.Inject

@InjectViewState
class SecondPresenter @Inject constructor(
        private val mainRouter: MainRouter,
        private val resourceManager: ResourceManager,
        globalRouter: GlobalRouter,
        statePublisher: ScreenInteractor
) : BasePresenter<BaseView>(globalRouter, statePublisher) {

    override fun attachView(view: BaseView) {
        super.attachView(view)
        requestAppearance(
                title = resourceManager.getString(R.string.title_second),
                pageMode = PageMode.TOOLBAR,
                toggle = Toggle.BACK,
                sideMode = SideMode.CLOSED,
                toolbarMenu = ToolbarMenu.NO_MENU)
    }

    override fun back() = mainRouter.exit()

}
