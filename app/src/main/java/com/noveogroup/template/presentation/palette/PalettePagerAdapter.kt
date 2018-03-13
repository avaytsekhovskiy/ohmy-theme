package com.noveogroup.template.presentation.palette

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.noveogroup.template.presentation.palette.page.PaletteTab
import com.noveogroup.template.presentation.palette.page.buttons.ButtonsFragment
import com.noveogroup.template.presentation.palette.page.pickers.PickersFragment
import com.noveogroup.template.presentation.palette.page.selectors.SelectorsFragment
import com.noveogroup.template.presentation.palette.page.texts.TextsFragment


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