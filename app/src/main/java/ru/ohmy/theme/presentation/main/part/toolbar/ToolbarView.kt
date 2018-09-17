package ru.ohmy.theme.presentation.main.part.toolbar

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.ohmy.theme.domain.interactor.state.model.PageMode
import ru.ohmy.theme.domain.interactor.state.model.Toggle
import ru.ohmy.theme.domain.interactor.state.model.ToolbarMenu
import ru.ohmy.theme.presentation.common.mvp.view.message.DebugView
import ru.ohmy.theme.presentation.common.mvp.view.message.ToastView

interface ToolbarView : DebugView, ToastView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeTitle(title: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeToggle(toggle: Toggle)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeToolbarMenu(toolbarMenu: ToolbarMenu)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changePageMode(pageMode: PageMode)
}
