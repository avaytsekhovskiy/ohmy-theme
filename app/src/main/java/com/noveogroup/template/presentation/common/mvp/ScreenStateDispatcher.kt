package com.noveogroup.template.presentation.common.mvp

import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.ScreenStateDiffHelper
import com.noveogroup.template.domain.interactor.state.model.*
import io.reactivex.android.schedulers.AndroidSchedulers

interface ScreenStateDispatcher {

    val stateInteractor: ScreenInteractor

    var previousState: ScreenState?

    fun observeAppearance() =
            stateInteractor.listenForChanges(AndroidSchedulers.mainThread()) {
                dispatchAppearance(it)
            }

    fun requestAppearanceRefresh() = dispatchAppearance(stateInteractor.state)

    fun requestAppearance(state: ScreenState) = stateInteractor.publish(state)

    fun requestAppearance(
            title: String? = null,
            toggle: Toggle? = null,
            toolbarMenu: ToolbarMenu? = null,
            pageMode: PageMode? = null,
            sideMode: SideMode? = null
    ) = stateInteractor.publish(title, toggle, toolbarMenu, pageMode, sideMode)

    private fun dispatchAppearance(state: ScreenState) {
        if (this is ScreenStateListener) {
            onScreenStateChanged(ScreenStateDiffHelper(state, previousState))
            previousState = state
        }
    }
}
