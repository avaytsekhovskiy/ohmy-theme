package com.noveogroup.template.presentation.palette.page

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.noveogroup.template.R

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
    TEXTS(R.drawable.ic_text, R.string.palette_tab_text)
}