package com.noveogroup.template.presentation.palette.page.pickers

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.noveogroup.template.presentation.palette.page.DisableDemoView


interface PickersView : DisableDemoView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showExplanation()

}
