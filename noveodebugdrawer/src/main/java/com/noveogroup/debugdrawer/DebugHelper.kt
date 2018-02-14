package com.noveogroup.debugdrawer

import android.app.Application
import com.noveogroup.debugdrawer.data.DeveloperPreferences
import com.noveogroup.debugdrawer.data.theme.Theme
import com.noveogroup.debugdrawer.data.theme.ThemeProxy
import io.palaima.debugdrawer.actions.SpinnerAction

class DebugHelper(app: Application) {

    private val developerPreferences = DeveloperPreferences(app)

    val themeProxy = ThemeProxy(developerPreferences.theme)

    fun createThemeSpinner(): SpinnerAction<Theme> {
        val values = Theme.values().toList()
        val current = developerPreferences.theme.read().let { Theme.values()[it.or(0)] }
        val selectedItem = values.indexOf(current)

        return SpinnerAction<Theme>(values, SpinnerAction.OnItemSelectedListener {
            developerPreferences.theme.save(it.ordinal)
        }, selectedItem)
    }

}
