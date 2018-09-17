package ru.ohmy.theme.presentation.common.mvp.view.message

import android.support.annotation.StringRes

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface ToastView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showToast(text: CharSequence)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showToast(@StringRes stringId: Int, vararg args: Any)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun cancelToast()
}
