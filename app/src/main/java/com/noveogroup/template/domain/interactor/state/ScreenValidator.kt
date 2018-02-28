package com.noveogroup.template.domain.interactor.state

import com.noveogroup.template.domain.interactor.state.model.PageMode
import com.noveogroup.template.domain.interactor.state.model.ScreenState
import com.noveogroup.template.domain.interactor.state.model.SideMode
import com.noveogroup.template.domain.interactor.state.model.ToolbarMenu

class ScreenValidator {

    fun autofix(
            title: String,
            toolbarMenu: ToolbarMenu,
            pageMode: PageMode,
            sideMode: SideMode
    ): ScreenState {
        val newSideMode = when {
            pageMode === com.noveogroup.template.domain.interactor.state.model.PageMode.FULLSCREEN_MODAL -> SideMode.DISABLED
            else -> sideMode
        }

        return ScreenState(title, toolbarMenu, pageMode, newSideMode)
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
