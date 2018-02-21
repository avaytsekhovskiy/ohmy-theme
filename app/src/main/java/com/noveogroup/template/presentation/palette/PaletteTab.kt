package com.noveogroup.template.presentation.palette

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
    SELECTORS(R.drawable.ic_save, R.string.palette_tab_selectors),
    BUTTONS(R.drawable.ic_mail, R.string.palette_tab_buttons),
    OVERVIEW(R.drawable.ic_sms, R.string.palette_tab_other),
    OTHER(R.drawable.ic_settings, R.string.palette_tab_other)
}