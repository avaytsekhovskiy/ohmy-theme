package com.noveogroup.template.domain.interactor.state.model

@Suppress("DataClassPrivateConstructor")
data class ScreenState(
        val title: String,
        val toolbarMenu: ToolbarMenu,
        val pageMode: PageMode,
        val sideMode: SideMode
)
