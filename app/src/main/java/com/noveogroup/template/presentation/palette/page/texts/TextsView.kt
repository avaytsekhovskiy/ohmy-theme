package com.noveogroup.template.presentation.palette.page.texts

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.noveogroup.template.presentation.palette.page.DisableDemoView


interface TextsView : DisableDemoView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showExplanation()

}
