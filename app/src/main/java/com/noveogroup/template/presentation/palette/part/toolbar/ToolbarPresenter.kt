package com.noveogroup.template.presentation.palette.part.toolbar

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.ScreenStateDiffHelper
import com.noveogroup.template.domain.interactor.state.model.ScreenState
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import com.noveogroup.template.presentation.common.mvp.ScreenStateListener
import com.noveogroup.template.presentation.main.part.toolbar.MenuItemDescriptor
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
