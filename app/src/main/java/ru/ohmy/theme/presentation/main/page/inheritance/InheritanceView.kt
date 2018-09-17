package ru.ohmy.theme.presentation.main.page.inheritance

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.ohmy.theme.presentation.common.mvp.view.BaseView


interface InheritanceView : BaseView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showExplanation()
}