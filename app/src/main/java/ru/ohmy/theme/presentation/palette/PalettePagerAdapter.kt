package ru.ohmy.theme.presentation.palette

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import ru.ohmy.theme.presentation.palette.page.PaletteTab
import ru.ohmy.theme.presentation.palette.page.buttons.ButtonsFragment
import ru.ohmy.theme.presentation.palette.page.pickers.PickersFragment
import ru.ohmy.theme.presentation.palette.page.selectors.SelectorsFragment
import ru.ohmy.theme.presentation.palette.page.texts.TextsFragment


class PalettePagerAdapter(
        private val tabs: Array<PaletteTab>,
        fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = when (tabs[position]) {
        PaletteTab.SELECTORS -> SelectorsFragment.newInstance()
        PaletteTab.BUTTONS -> ButtonsFragment.newInstance()
        PaletteTab.PICKERS -> PickersFragment.newInstance()
        PaletteTab.TEXTS -> TextsFragment.newInstance()
    }

    override fun getCount() = tabs.size

}