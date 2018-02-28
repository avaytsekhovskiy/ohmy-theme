package com.noveogroup.template.presentation.palette

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.noveogroup.template.presentation.common.mvp.view.BaseView
import com.noveogroup.template.presentation.common.mvp.view.RecreateView
import com.noveogroup.template.presentation.palette.page.PaletteTab

interface PaletteView : BaseView, RecreateView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun selectTab(position: PaletteTab)

}
