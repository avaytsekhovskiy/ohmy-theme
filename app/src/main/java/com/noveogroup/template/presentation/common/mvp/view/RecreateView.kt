package com.noveogroup.template.presentation.common.mvp.view

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface RecreateView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideSettings()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun recreate()

}