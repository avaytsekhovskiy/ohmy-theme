package com.noveogroup.template.presentation.palette.page

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.noveogroup.template.R
import com.noveogroup.template.domain.navigation.router.PaletteRouter

/**
 * Created by avaytsekhovskiy on 16/02/2018.
 */
enum class PaletteTab(
        @DrawableRes val drawableRes: Int,
        @StringRes val stringRes: Int
) {
    SELECTORS(R.drawable.ic_radio_button, R.string.palette_tab_selectors),
    BUTTONS(R.drawable.ic_button, R.string.palette_tab_buttons),
    PICKERS(R.drawable.ic_star_half, R.string.palette_tab_pickers),
    TEXTS(R.drawable.ic_text, R.string.palette_tab_text);

    companion object {
        @JvmStatic
        fun findByScreenName(name: String) = when (name) {
            PaletteRouter.BUTTONS -> BUTTONS
            PaletteRouter.SELECTORS -> SELECTORS
            PaletteRouter.PICKERS -> PICKERS
            PaletteRouter.TEXTS -> TEXTS
            else -> valueOf(name)
        }
    }
}