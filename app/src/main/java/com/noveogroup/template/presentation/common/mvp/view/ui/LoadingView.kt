package com.noveogroup.template.presentation.common.mvp.view.ui

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.noveogroup.template.presentation.common.mvp.strategy.AddToEndSingleByTagStrategy

interface LoadingView : MvpView {

    @StateStrategyType(value = AddToEndSingleByTagStrategy::class, tag = TAG_CONTENT)
    fun showRegularContent()

    @StateStrategyType(value = AddToEndSingleByTagStrategy::class, tag = TAG_CONTENT)
    fun showErrorContent()

    @StateStrategyType(value = AddToEndSingleByTagStrategy::class, tag = TAG_CONTENT)
    fun showEmptyContent()

    @StateStrategyType(value = AddToEndSingleByTagStrategy::class, tag = TAG_CONTENT)
    fun hideContent()

    @StateStrategyType(value = AddToEndSingleByTagStrategy::class, tag = TAG_PROGRESS)
    fun showProgressWithMask()

    @StateStrategyType(value = AddToEndSingleByTagStrategy::class, tag = TAG_PROGRESS)
    fun hideProgressWithMask()

    companion object {
        const val TAG_CONTENT = "LoadingDelegate :: content"
        const val TAG_PROGRESS = "LoadingDelegate :: progress"
    }

}
