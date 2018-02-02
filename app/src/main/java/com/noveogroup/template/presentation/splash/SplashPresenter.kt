package com.noveogroup.template.presentation.splash

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.model.PageMode
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import io.reactivex.Completable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class SplashPresenter @Inject constructor(
        globalRouter: GlobalRouter,
        statePublisher: ScreenInteractor
) : BasePresenter<SplashView>(globalRouter, statePublisher) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Completable.timer(1, TimeUnit.SECONDS)
                .subscribe(globalRouter::startFromMain)
                .unsubscribeOnDestroy()
    }

    override fun attachView(view: SplashView) {
        super.attachView(view)
        requestAppearance(pageMode = PageMode.FULLSCREEN_MODAL)
        viewState.hideKeyboard()
    }
}
