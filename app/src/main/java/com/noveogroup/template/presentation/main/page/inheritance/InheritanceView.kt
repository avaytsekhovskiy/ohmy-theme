package com.noveogroup.template.presentation.main.page.inheritance

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.noveogroup.template.presentation.common.mvp.view.BaseView


interface InheritanceView : BaseView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showExplanation()
}