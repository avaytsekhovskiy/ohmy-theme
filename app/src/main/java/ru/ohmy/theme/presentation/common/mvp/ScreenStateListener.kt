package ru.ohmy.theme.presentation.common.mvp

import ru.ohmy.theme.domain.interactor.state.ScreenInteractor
import ru.ohmy.theme.domain.interactor.state.ScreenStateDiffHelper
import ru.ohmy.theme.domain.interactor.state.model.ScreenState
import io.reactivex.android.schedulers.AndroidSchedulers

interface ScreenStateListener {

    val screenInteractor: ScreenInteractor

    var previousState: ScreenState?

    fun observeAppearance() =
            screenInteractor.listenForChanges(AndroidSchedulers.mainThread()) {
                dispatchAppearance(it)
            }

    fun requestAppearanceRefresh() = dispatchAppearance(screenInteractor.state)

    fun dispatchAppearance(state: ScreenState) {
        onScreenStateChanged(ScreenStateDiffHelper(state, previousState))
        previousState = state
    }

    fun onScreenStateChanged(helper: ScreenStateDiffHelper)
}
