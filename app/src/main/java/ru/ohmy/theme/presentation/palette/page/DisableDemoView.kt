package ru.ohmy.theme.presentation.palette.page

import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.ohmy.theme.presentation.common.mvp.strategy.AddToEndSingleByTagStrategy
import ru.ohmy.theme.presentation.common.mvp.view.BaseView


interface DisableDemoView : BaseView {

    @StateStrategyType(AddToEndSingleByTagStrategy::class, tag = TAG_ENABLED)
    fun enableViews()

    @StateStrategyType(AddToEndSingleByTagStrategy::class, tag = TAG_ENABLED)
    fun disableViews()

    companion object {
        private const val TAG_ENABLED = "TAG VIEWS ENABLED"
    }

}