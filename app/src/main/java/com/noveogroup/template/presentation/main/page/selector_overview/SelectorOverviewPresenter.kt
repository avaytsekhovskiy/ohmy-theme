package com.noveogroup.template.presentation.main.page.selector_overview

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.R
import com.noveogroup.template.data.android.system.ResourceManager
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.model.SideMode
import com.noveogroup.template.domain.interactor.state.model.Toggle
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
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
                toggle = Toggle.BACK,
                sideMode = SideMode.DISABLED,
                title = resourceManager.getString(R.string.palette_title_selector_overview)
        )
    }

}