package com.noveogroup.template.presentation.palette.selectors

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.R
import com.noveogroup.template.core.ext.observeSafe
import com.noveogroup.template.data.android.system.ResourceManager
import com.noveogroup.template.domain.interactor.PaletteInteractor
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.model.SideMode
import com.noveogroup.template.domain.interactor.state.model.Toggle
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@InjectViewState
class SelectorsPresenter @Inject constructor(
        private val paletteInteractor: PaletteInteractor,
        private val resourceManager: ResourceManager,
        private val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<SelectorsView>(globalRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        paletteInteractor.observeExplain()
                .observeSafe(AndroidSchedulers.mainThread()) {
                    viewState.showExplanation()
                }
                .unsubscribeOnDestroy()

        paletteInteractor.observeDisable()
                .observeSafe(AndroidSchedulers.mainThread()) {
                    if (it) viewState.disableViews()
                    else viewState.enableViews()
                }
                .unsubscribeOnDestroy()
    }

    override fun attachView(view: SelectorsView?) {
        super.attachView(view)
        screenInteractor.publish(
                toggle = Toggle.BACK,
                sideMode = SideMode.DISABLED,
                title = resourceManager.getString(R.string.palette_title_selectors)
        )
    }

}