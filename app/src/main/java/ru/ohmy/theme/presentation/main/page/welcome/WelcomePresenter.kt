package ru.ohmy.theme.presentation.main.page.welcome

import com.arellomobile.mvp.InjectViewState
import ru.ohmy.theme.R
import ru.ohmy.theme.data.android.system.ResourceManager
import ru.ohmy.theme.domain.interactor.state.ScreenInteractor
import ru.ohmy.theme.domain.interactor.state.model.SideMode
import ru.ohmy.theme.domain.interactor.state.model.Toggle
import ru.ohmy.theme.domain.navigation.router.GlobalRouter
import ru.ohmy.theme.domain.navigation.router.MainRouter
import ru.ohmy.theme.presentation.common.mvp.BasePresenter
import ru.ohmy.theme.presentation.common.mvp.view.BaseView
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
