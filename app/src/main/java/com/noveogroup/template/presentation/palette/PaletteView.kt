package com.noveogroup.template.presentation.palette

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.noveogroup.template.presentation.common.mvp.strategy.AddToEndSingleByTagStrategy
import com.noveogroup.template.presentation.common.mvp.view.BaseView

/**
 * Created by avaytsekhovskiy on 12/02/2018.
 */
interface PaletteView : BaseView {

    @StateStrategyType(AddToEndSingleByTagStrategy::class, tag = TAG_CONTROLS)
    fun disableUiControls()

    @StateStrategyType(AddToEndSingleByTagStrategy::class, tag = TAG_CONTROLS)
    fun enableUiControls()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showLightDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showDarkDialog()

    companion object {
        private const val TAG_CONTROLS = "Controls::State"
    }
}