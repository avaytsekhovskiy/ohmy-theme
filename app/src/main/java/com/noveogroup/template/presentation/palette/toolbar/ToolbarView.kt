package com.noveogroup.template.presentation.palette.toolbar

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.noveogroup.template.presentation.common.mvp.view.message.DebugView
import com.noveogroup.template.presentation.common.mvp.view.message.ToastView

interface ToolbarView : DebugView, ToastView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeTitle(title: String)
}
