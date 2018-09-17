package ru.ohmy.theme.presentation.main

import com.arellomobile.mvp.InjectViewState
import ru.ohmy.theme.core.ext.observeSafe
import ru.ohmy.theme.domain.interactor.ThemeInteractor
import ru.ohmy.theme.domain.interactor.state.ScreenInteractor
import ru.ohmy.theme.domain.interactor.state.model.PageMode
import ru.ohmy.theme.domain.interactor.state.model.ToolbarMenu
import ru.ohmy.theme.domain.navigation.router.GlobalRouter
import ru.ohmy.theme.domain.navigation.router.MainRouter
import ru.ohmy.theme.presentation.common.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
        private val themeInteractor: ThemeInteractor,
        private val mainRouter: MainRouter,
        private val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<MainView>(globalRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        mainRouter.startFromWelcome()

        themeInteractor.observeSettings()
                .observeSafe(AndroidSchedulers.mainThread()) { viewState.recreate() }
                .unsubscribeOnDestroy()
    }

    override fun attachView(view: MainView?) {
        super.attachView(view)

        screenInteractor.publish(
                toolbarMenu = ToolbarMenu.MAIN_MENU,
                pageMode = PageMode.TOOLBAR
        )
    }

    override fun back() = mainRouter.exit()

}
