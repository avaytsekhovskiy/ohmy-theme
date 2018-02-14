package com.noveogroup.debugdrawer.data

import android.app.Application
import com.noveogroup.preferences.NoveoPreferences
import com.noveogroup.preferences.api.Preference

/**
 * Created by avaytsekhovskiy on 14/02/2018.
 */
class DeveloperPreferences(context: Application) {

    private val factory = NoveoPreferences(context)

    val theme: Preference<Int> = factory.getInt("theme id", 0)

}
