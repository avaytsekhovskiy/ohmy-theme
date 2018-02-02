package com.noveogroup.template.presentation.main.page.welcome

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.noveogroup.template.presentation.common.mvp.view.BaseView


interface WelcomeView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showName(name: String)
}
