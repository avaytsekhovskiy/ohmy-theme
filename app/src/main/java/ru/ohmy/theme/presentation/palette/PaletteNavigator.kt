package ru.ohmy.theme.presentation.palette


import android.support.v4.view.ViewPager
import ru.ohmy.theme.presentation.common.navigation.SupportNavigator
import ru.ohmy.theme.presentation.palette.page.PaletteTab
import ru.terrakok.cicerone.commands.Replace

@Suppress("MemberVisibilityCanBePrivate")
class PaletteNavigator(
        private val pager: ViewPager
) : SupportNavigator() {

    override fun replace(replace: Replace) {
        PaletteTab
                .findByScreenName(replace.screenKey)
                .let { pager.currentItem = it.ordinal }
    }
}