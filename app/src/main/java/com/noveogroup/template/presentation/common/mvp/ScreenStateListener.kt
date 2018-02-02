package com.noveogroup.template.presentation.common.mvp

import com.noveogroup.template.domain.interactor.state.ScreenStateDiffHelper

interface ScreenStateListener {
    fun onScreenStateChanged(helper: ScreenStateDiffHelper)
}
