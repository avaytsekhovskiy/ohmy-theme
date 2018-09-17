package ru.ohmy.theme.presentation.splash

import com.arellomobile.mvp.InjectViewState
import ru.ohmy.theme.domain.navigation.router.GlobalRouter
import ru.ohmy.theme.presentation.common.mvp.BasePresenter
import ru.ohmy.theme.presentation.common.mvp.view.BaseView
import javax.inject.Inject

@InjectViewState
class SplashPresenter @Inject constructor(
        globalRouter: GlobalRouter
) : BasePresenter<BaseView>(globalRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        globalRouter.startFromMain()
    }
}
