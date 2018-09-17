package ru.ohmy.theme.presentation.common.android.menu

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.noveogroup.debugdrawer.data.theme.Theme
import ru.ohmy.theme.presentation.common.mvp.view.BaseView
import ru.ohmy.theme.presentation.common.mvp.view.ui.DrawerView

interface AppMenuView : BaseView, DrawerView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun selectChoice(theme: Theme)
}
