package ru.ohmy.theme.domain.interactor

import com.jakewharton.rxrelay2.BehaviorRelay
import com.noveogroup.debugdrawer.data.theme.Theme
import com.noveogroup.debugdrawer.data.theme.ThemeProxy
import io.reactivex.Observable
import javax.inject.Inject


class ThemeInteractor @Inject constructor(
        private val themeProxy: ThemeProxy
) {

    private val themeRelay = BehaviorRelay.create<Theme>().apply {
        accept(themeProxy.read())
        themeProxy.changes().subscribe { accept(it) }
    }

    val currentTheme: Theme get() = themeRelay.value

    fun observeSettings(): Observable<Theme> = themeRelay.skip(1)

    fun changeTheme(theme: Theme): Unit = themeProxy.change(theme)
}