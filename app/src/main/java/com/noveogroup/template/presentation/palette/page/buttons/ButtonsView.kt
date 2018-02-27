package com.noveogroup.template.presentation.palette.page.buttons

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.noveogroup.template.presentation.palette.DisableDemoView


interface ButtonsView : DisableDemoView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showLightDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showDarkDialog()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showExplanation()
}
