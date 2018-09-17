package ru.ohmy.theme.presentation.main.page.theme_overview

import com.arellomobile.mvp.InjectViewState
import ru.ohmy.theme.R
import ru.ohmy.theme.data.android.system.ResourceManager
import ru.ohmy.theme.domain.interactor.state.ScreenInteractor
import ru.ohmy.theme.domain.interactor.state.model.SideMode
import ru.ohmy.theme.domain.interactor.state.model.Toggle
import ru.ohmy.theme.domain.navigation.router.GlobalRouter
import ru.ohmy.theme.presentation.common.mvp.BasePresenter
import javax.inject.Inject

@InjectViewState
class ThemeOverviewPresenter @Inject constructor(
        private val screenInteractor: ScreenInteractor,
        private val resourceManager: ResourceManager,
        globalRouter: GlobalRouter
) : BasePresenter<ThemeOverviewView>(globalRouter) {

    override fun attachView(view: ThemeOverviewView?) {
        super.attachView(view)
        screenInteractor.publish(
                sideMode = SideMode.DISABLED,
                toggle = Toggle.BACK,
                title = resourceManager.getString(R.string.palette_title_theme_overview)
        )
    }

}