package ru.ohmy.theme.presentation.common.mvp.view

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface RecreateView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun recreate()

}