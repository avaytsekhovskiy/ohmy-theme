package ru.ohmy.theme.presentation.palette.part.toolbar

import com.arellomobile.mvp.InjectViewState
import ru.ohmy.theme.domain.interactor.state.ScreenInteractor
import ru.ohmy.theme.domain.interactor.state.ScreenStateDiffHelper
import ru.ohmy.theme.domain.interactor.state.model.ScreenState
import ru.ohmy.theme.domain.navigation.router.GlobalRouter
import ru.ohmy.theme.presentation.common.mvp.BasePresenter
import ru.ohmy.theme.presentation.common.mvp.ScreenStateListener
import ru.ohmy.theme.presentation.main.part.toolbar.MenuItemDescriptor
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@InjectViewState
class ToolbarPresenter @Inject constructor(
        override val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<ToolbarView>(globalRouter), ScreenStateListener {

    override var previousState: ScreenState? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        screenInteractor
                .listenForChanges(AndroidSchedulers.mainThread(), ::dispatchAppearance)
                .unsubscribeOnDestroy()
    }

    override fun onScreenStateChanged(helper: ScreenStateDiffHelper) = with(helper) {
        ifTitleChanged(viewState::changeTitle)
    }

    override fun back() = globalRouter.exit()

    @Suppress("NON_EXHAUSTIVE_WHEN")
    fun handleMenuItemClick(item: MenuItemDescriptor) = with(screenInteractor) {
        if (item == MenuItemDescriptor.SETTINGS) {
            publish(sideMode = state.sideMode.toggle())
        }
    }

}
