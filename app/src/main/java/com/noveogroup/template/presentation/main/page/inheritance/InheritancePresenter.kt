package com.noveogroup.template.presentation.main.page.inheritance

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
class InheritancePresenter @Inject constructor(
        private val resourceManager: ResourceManager,
        private val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<InheritanceView>(globalRouter) {

    override fun attachView(view: InheritanceView?) {
        super.attachView(view)
        screenInteractor.publish(
                sideMode = SideMode.DISABLED,
                toggle = Toggle.BACK,
                title = resourceManager.getString(R.string.title_inheritance)
        )
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.takeIf { shouldShowExplanation }?.showExplanation()
        shouldShowExplanation = false
    }

    companion object {
        var shouldShowExplanation = true
    }
}