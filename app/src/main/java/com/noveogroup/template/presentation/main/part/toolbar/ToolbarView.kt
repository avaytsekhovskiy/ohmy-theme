package com.noveogroup.template.presentation.main.part.toolbar

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.noveogroup.template.domain.interactor.state.model.PageMode
import com.noveogroup.template.domain.interactor.state.model.Toggle
import com.noveogroup.template.domain.interactor.state.model.ToolbarMenu
import com.noveogroup.template.presentation.common.mvp.view.message.DebugView
import com.noveogroup.template.presentation.common.mvp.view.message.ToastView

interface ToolbarView : DebugView, ToastView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeTitle(title: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeToolbarMenu(toolbarMenu: ToolbarMenu)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeToggle(icon: Toggle)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changePageMode(pageMode: PageMode)
}
