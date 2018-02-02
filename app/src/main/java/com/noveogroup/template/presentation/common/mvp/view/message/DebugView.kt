package com.noveogroup.template.presentation.common.mvp.view.message

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface DebugView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun showDebugMessage(comment: CharSequence = "", error: Throwable? = null)
}
