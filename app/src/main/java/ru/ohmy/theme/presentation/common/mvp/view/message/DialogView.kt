package ru.ohmy.theme.presentation.common.mvp.view.message

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface DialogView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSimpleDialog(title: String, description: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun cancelDialog()
}
