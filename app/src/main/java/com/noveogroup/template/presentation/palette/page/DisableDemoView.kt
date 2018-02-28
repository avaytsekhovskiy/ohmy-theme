package com.noveogroup.template.presentation.palette.page

import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.noveogroup.template.presentation.common.mvp.strategy.AddToEndSingleByTagStrategy
import com.noveogroup.template.presentation.common.mvp.view.BaseView


interface DisableDemoView : BaseView {

    @StateStrategyType(AddToEndSingleByTagStrategy::class, tag = TAG_ENABLED)
    fun enableViews()

    @StateStrategyType(AddToEndSingleByTagStrategy::class, tag = TAG_ENABLED)
    fun disableViews()

    companion object {
        private const val TAG_ENABLED = "TAG VIEWS ENABLED"
    }

}