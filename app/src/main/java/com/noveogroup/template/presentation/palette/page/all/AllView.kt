package com.noveogroup.template.presentation.palette.page.all

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.noveogroup.template.presentation.palette.DisableDemoView


interface AllView : DisableDemoView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showExplanation()

}
