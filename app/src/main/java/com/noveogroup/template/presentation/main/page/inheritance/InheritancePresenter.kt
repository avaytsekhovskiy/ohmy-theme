package com.noveogroup.template.presentation.main.page.inheritance

import com.noveogroup.template.R
import com.noveogroup.template.data.android.system.ResourceManager
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.model.SideMode
import com.noveogroup.template.domain.interactor.state.model.Toggle
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import com.noveogroup.template.presentation.common.mvp.view.BaseView
import javax.inject.Inject


class InheritancePresenter @Inject constructor(
        private val resourceManager: ResourceManager,
        private val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<BaseView>(globalRouter) {

    override fun attachView(view: BaseView?) {
        super.attachView(view)
        screenInteractor.publish(
                toggle = Toggle.BACK,
                sideMode = SideMode.DISABLED,
                title = resourceManager.getString(R.string.title_inheritance)
        )
    }
}