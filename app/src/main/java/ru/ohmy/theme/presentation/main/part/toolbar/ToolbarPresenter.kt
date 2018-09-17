package ru.ohmy.theme.presentation.main.part.toolbar

import com.arellomobile.mvp.InjectViewState
import ru.ohmy.theme.domain.interactor.state.ScreenInteractor
import ru.ohmy.theme.domain.interactor.state.ScreenStateDiffHelper
import ru.ohmy.theme.domain.interactor.state.model.ScreenState
import ru.ohmy.theme.domain.navigation.router.GlobalRouter
import ru.ohmy.theme.domain.navigation.router.MainRouter
import ru.ohmy.theme.presentation.common.mvp.BasePresenter
import ru.ohmy.theme.presentation.common.mvp.ScreenStateListener
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@InjectViewState
class ToolbarPresenter @Inject constructor(
        private val mainRouter: MainRouter,
        override val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<ToolbarView>(globalRouter), ScreenStateListener {

    override var previousState: ScreenState? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        screenInteractor
                .listenForChanges(AndroidSchedulers.mainThread(), ::dispatchAppearance)
                .unsubscribeOnDestroy()

        observeAppearance().unsubscribeOnDestroy()
    }

    override fun onScreenStateChanged(helper: ScreenStateDiffHelper) = with(helper) {
        ifPageModeChanged(viewState::changePageMode)
        ifToggleChanged(viewState::changeToggle)
        ifTitleChanged(viewState::changeTitle)
        ifToolbarMenuChanged(viewState::changeToolbarMenu)
    }

    override fun back() = mainRouter.exit()

    fun handleMenuItemClick(menuItem: MenuItemDescriptor) {
        if (menuItem == MenuItemDescriptor.SETTINGS) {
            toggleMenu()
        }
    }

    private fun toggleMenu(): Unit = with(screenInteractor) {
        publish(sideMode = state.sideMode.toggle())
    }

}
