package ru.ohmy.theme.domain.interactor.state.model

@Suppress("DataClassPrivateConstructor")
data class ScreenState(
        val title: String,
        val toggle: Toggle,
        val toolbarMenu: ToolbarMenu,
        val pageMode: PageMode,
        val sideMode: SideMode
)
