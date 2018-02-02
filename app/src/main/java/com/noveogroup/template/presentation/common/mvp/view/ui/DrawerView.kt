package com.noveogroup.template.presentation.common.mvp.view.ui

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.noveogroup.template.presentation.common.mvp.strategy.AddToEndSingleByTagStrategy

interface DrawerView : MvpView {

    @StateStrategyType(value = AddToEndSingleByTagStrategy::class, tag = TAG_TOGGLE)
    fun open()

    @StateStrategyType(value = AddToEndSingleByTagStrategy::class, tag = TAG_TOGGLE)
    fun close()

    @StateStrategyType(value = AddToEndSingleStrategy::class, tag = TAG_LOCK)
    fun lockOpened()

    @StateStrategyType(value = AddToEndSingleStrategy::class, tag = TAG_LOCK)
    fun lockClosed()

    @StateStrategyType(value = AddToEndSingleStrategy::class, tag = TAG_LOCK)
    fun lockAsIs()

    @StateStrategyType(value = AddToEndSingleByTagStrategy::class, tag = TAG_LOCK)
    fun unlock()

    companion object {
        const val TAG_TOGGLE = "DrawerView :: toggle"
        const val TAG_LOCK = "DrawerView :: lock"
    }
}
