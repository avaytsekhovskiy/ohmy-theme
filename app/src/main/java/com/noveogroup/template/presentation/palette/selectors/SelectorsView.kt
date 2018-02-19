package com.noveogroup.template.presentation.palette.selectors

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.noveogroup.template.presentation.palette.DisableDemoView


interface SelectorsView : DisableDemoView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showExplanation()
}
