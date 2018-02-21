package com.noveogroup.template.presentation.palette.overview

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.R
import com.noveogroup.template.data.android.system.ResourceManager
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import javax.inject.Inject

@InjectViewState
class OverviewPresenter @Inject constructor(
        private val screenInteractor: ScreenInteractor,
        private val resourceManager: ResourceManager,
        globalRouter: GlobalRouter
) : BasePresenter<OverviewView>(globalRouter) {

    override fun attachView(view: OverviewView?) {
        super.attachView(view)

        screenInteractor.publish(
                title = resourceManager.getString(R.string.palette_title_overview)
        )
    }

}