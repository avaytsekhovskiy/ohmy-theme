package ru.ohmy.theme.presentation.palette

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.ohmy.theme.presentation.common.mvp.view.BaseView
import ru.ohmy.theme.presentation.common.mvp.view.RecreateView
import ru.ohmy.theme.presentation.palette.page.PaletteTab

interface PaletteView : BaseView, RecreateView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun selectTab(position: PaletteTab)

}
