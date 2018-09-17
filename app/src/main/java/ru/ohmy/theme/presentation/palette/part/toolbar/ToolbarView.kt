package ru.ohmy.theme.presentation.palette.part.toolbar

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.ohmy.theme.presentation.common.mvp.view.message.DebugView
import ru.ohmy.theme.presentation.common.mvp.view.message.ToastView

interface ToolbarView : DebugView, ToastView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeTitle(title: String)
}
