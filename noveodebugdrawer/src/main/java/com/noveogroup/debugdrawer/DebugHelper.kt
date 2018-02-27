package com.noveogroup.debugdrawer

import android.app.Application
import com.noveogroup.debugdrawer.data.DeveloperPreferences
import com.noveogroup.debugdrawer.data.theme.Theme
import com.noveogroup.debugdrawer.data.theme.ThemeProxy
import io.palaima.debugdrawer.actions.SpinnerAction

class DebugHelper(app: Application) {

    private val developerPreferences = DeveloperPreferences(app)

    val themeProxy = ThemeProxy(developerPreferences.theme)

    fun createThemeSpinner() = Theme.values().toList().let {
        val current = it[developerPreferences.theme.read().or(0)]
        val listener = SpinnerAction.OnItemSelectedListener<Theme> {
            developerPreferences.theme.save(it.ordinal)
        }
        return@let SpinnerAction<Theme>(it, listener, it.indexOf(current))
    }

}
