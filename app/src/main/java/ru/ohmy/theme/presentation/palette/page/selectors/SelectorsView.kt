package ru.ohmy.theme.presentation.palette.page.selectors

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.ohmy.theme.presentation.palette.page.DisableDemoView


interface SelectorsView : DisableDemoView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showExplanation()
}
