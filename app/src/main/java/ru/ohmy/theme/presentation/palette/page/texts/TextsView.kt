package ru.ohmy.theme.presentation.palette.page.texts

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.ohmy.theme.presentation.palette.page.DisableDemoView


interface TextsView : DisableDemoView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showExplanation()

}
