package ru.ohmy.theme.presentation.main.page.selector_overview

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
class SelectorOverviewPresenter @Inject constructor(
        private val screenInteractor: ScreenInteractor,
        private val resourceManager: ResourceManager,
        globalRouter: GlobalRouter
) : BasePresenter<SelectorOverviewView>(globalRouter) {

    override fun attachView(view: SelectorOverviewView?) {
        super.attachView(view)
        screenInteractor.publish(
                sideMode = SideMode.DISABLED,
                toggle = Toggle.BACK,
                title = resourceManager.getString(R.string.palette_title_selector_overview)
        )
    }

}