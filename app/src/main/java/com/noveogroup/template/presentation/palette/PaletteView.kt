package com.noveogroup.template.presentation.palette

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.noveogroup.template.presentation.common.mvp.view.BaseView

interface PaletteView : BaseView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSettings()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun recreate()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun selectTab(position: PaletteTab)

}
