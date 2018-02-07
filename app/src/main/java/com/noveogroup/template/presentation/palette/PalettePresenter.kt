package com.noveogroup.template.presentation.palette

import com.noveogroup.template.R
import com.noveogroup.template.data.android.system.ResourceManager
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.model.PageMode
import com.noveogroup.template.domain.interactor.state.model.SideMode
import com.noveogroup.template.domain.interactor.state.model.Toggle
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import com.noveogroup.template.presentation.common.mvp.view.BaseView
import javax.inject.Inject

class PalettePresenter @Inject constructor(
        private val resourceManager: ResourceManager,
        globalRouter: GlobalRouter,
        screenInteractor: ScreenInteractor
) : BasePresenter<BaseView>(globalRouter, screenInteractor) {

    override fun attachView(view: BaseView?) {
        super.attachView(view)

        requestAppearance(
                title = resourceManager.getString(R.string.palette_title),
                toggle = Toggle.BACK,
                pageMode = PageMode.TOOLBAR,
                sideMode = SideMode.DISABLED
        )
    }

    override fun back() = globalRouter.exit()
}
