package com.noveogroup.template.presentation.common.android.menu

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.noveogroup.debugdrawer.data.theme.Theme
import com.noveogroup.template.presentation.common.mvp.view.BaseView
import com.noveogroup.template.presentation.common.mvp.view.ui.DrawerView

interface AppMenuView : BaseView, DrawerView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun selectChoice(theme: Theme)
}
