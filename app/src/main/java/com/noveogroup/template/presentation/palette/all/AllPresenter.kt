package com.noveogroup.template.presentation.palette.all

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
class AllPresenter @Inject constructor(
        private val resourceManager: ResourceManager,
        private val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<AllView>(globalRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.enableViews()
    }

    override fun attachView(view: AllView?) {
        super.attachView(view)
        screenInteractor.publish(
                toggle = Toggle.BACK,
                sideMode = SideMode.DISABLED,
                title = resourceManager.getString(R.string.title_inheritance)
        )
    }

    fun requestDisable(disabled: Boolean) = with(viewState) {
        if (disabled) disableViews()
        else enableViews()
    }

}