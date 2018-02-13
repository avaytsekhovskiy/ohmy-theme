package com.noveogroup.template.presentation.palette

import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.noveogroup.template.presentation.common.mvp.strategy.AddToEndSingleByTagStrategy
import com.noveogroup.template.presentation.common.mvp.view.BaseView

/**
 * Created by avaytsekhovskiy on 13/02/2018.
 */
interface DisableDemoView : BaseView {

    @StateStrategyType(AddToEndSingleByTagStrategy::class, tag = TAG_ENABLED)
    fun enableViews()

    @StateStrategyType(AddToEndSingleByTagStrategy::class, tag = TAG_ENABLED)
    fun disableViews()

    companion object {
        private const val TAG_ENABLED = "TAG VIEWS ENABLED"
    }

}