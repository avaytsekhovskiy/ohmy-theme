package com.noveogroup.template.presentation.palette.page.all

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.R
import com.noveogroup.template.core.ext.observeSafe
import com.noveogroup.template.data.android.system.ResourceManager
import com.noveogroup.template.domain.interactor.PaletteInteractor
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@InjectViewState
class AllPresenter @Inject constructor(
        private val paletteInteractor: PaletteInteractor,
        private val resourceManager: ResourceManager,
        private val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<AllView>(globalRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.enableViews()

        paletteInteractor.observeExplain()
                .observeSafe(AndroidSchedulers.mainThread()) {
                    viewState.showExplanation()
                }
                .unsubscribeOnDestroy()

        paletteInteractor.observeEnable()
                .observeSafe(AndroidSchedulers.mainThread()) { enabled ->
                    if (enabled) viewState.enableViews()
                    else viewState.disableViews()
                }
                .unsubscribeOnDestroy()
    }

    override fun attachView(view: AllView?) {
        super.attachView(view)
        screenInteractor.publish(
                title = resourceManager.getString(R.string.palette_title_other)
        )
    }

}