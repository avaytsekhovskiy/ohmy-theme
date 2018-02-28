package com.noveogroup.template.domain.interactor.state

import com.noveogroup.template.domain.interactor.state.model.*

class ScreenValidator {

    fun autofix(
            title: String,
            toggle: Toggle,
            toolbarMenu: ToolbarMenu,
            pageMode: PageMode,
            sideMode: SideMode
    ): ScreenState {
        val newSideMode = when {
            pageMode === PageMode.FULLSCREEN_MODAL -> SideMode.DISABLED
            else -> sideMode
        }

        return ScreenState(title, toggle, toolbarMenu, pageMode, newSideMode)
    }

    fun check(state: ScreenState): Unit = with(state) {
        when {
            pageMode === PageMode.FULLSCREEN_MODAL -> checkSideModeDisabledOrThrow("$pageMode incompatible with $sideMode")
        }
    }

    private fun ScreenState.checkSideModeDisabledOrThrow(msg: String) {
        if (sideMode !== SideMode.DISABLED) throw Error(msg)
    }
}
