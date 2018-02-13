package com.noveogroup.template.presentation.palette.buttons

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.R
import com.noveogroup.template.core.ext.observeSafe
import com.noveogroup.template.data.android.system.ResourceManager
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.model.SideMode
import com.noveogroup.template.domain.interactor.state.model.Toggle
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class ButtonsPresenter @Inject constructor(
        private val resourceManager: ResourceManager,
        private val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<ButtonsView>(globalRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Observable.interval(2, TimeUnit.SECONDS)
                .observeSafe(AndroidSchedulers.mainThread()) {
                    if (it % 2 == 0L) viewState.disableViews()
                    else viewState.enableViews()
                }
                .unsubscribeOnDestroy()
    }

    override fun attachView(view: ButtonsView?) {
        super.attachView(view)
        screenInteractor.publish(
                toggle = Toggle.BACK,
                sideMode = SideMode.DISABLED,
                title = resourceManager.getString(R.string.title_inheritance)
        )
    }

    fun requestExplanation() = viewState.showExplanation()

    fun requestDialog(light: Boolean) = with(viewState) {
        if (light) showLightDialog()
        else showDarkDialog()
    }

}