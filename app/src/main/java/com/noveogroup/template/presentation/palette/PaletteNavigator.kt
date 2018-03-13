package com.noveogroup.template.presentation.palette


import android.support.v4.view.ViewPager
import com.noveogroup.template.presentation.common.navigation.SupportNavigator
import com.noveogroup.template.presentation.palette.page.PaletteTab
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