package com.noveogroup.template.presentation.main.page.welcome

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.R
import com.noveogroup.template.data.android.system.ResourceManager
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.model.SideMode
import com.noveogroup.template.domain.interactor.state.model.Toggle
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.domain.navigation.router.MainRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import com.noveogroup.template.presentation.common.mvp.view.BaseView
import javax.inject.Inject

@InjectViewState
class WelcomePresenter @Inject constructor(
        private val mainRouter: MainRouter,
        private val resourceManager: ResourceManager,
        private val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<BaseView>(globalRouter) {

    override fun attachView(view: BaseView?) {
        super.attachView(view)
        screenInteractor.publish(
                sideMode = SideMode.CLOSED,
                toggle = Toggle.HIDDEN,
                title = resourceManager.getString(R.string.title_welcome))
    }

    internal fun openInheritance() = mainRouter.openInheritance()

    internal fun openThemeOverview() = mainRouter.openThemeOverview()

    internal fun openSelectorOverview() = mainRouter.openSelectorOverview()

    internal fun openPalette() = globalRouter.nextPalette()

}
