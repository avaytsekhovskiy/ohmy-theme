package com.noveogroup.debugdrawer.data

import android.app.Application
import com.noveogroup.preferences.NoveoPreferences
import com.noveogroup.preferences.api.Preference

class DeveloperPreferences(context: Application) {

    private val factory = NoveoPreferences(context)

    val theme: Preference<Int> = factory.getInt("theme id", 0)

}
