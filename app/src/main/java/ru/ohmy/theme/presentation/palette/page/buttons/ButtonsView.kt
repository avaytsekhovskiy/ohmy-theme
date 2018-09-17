package ru.ohmy.theme.presentation.palette.page.buttons

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.ohmy.theme.presentation.palette.page.DisableDemoView


interface ButtonsView : DisableDemoView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showLightDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showDarkDialog()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showExplanation()
}
