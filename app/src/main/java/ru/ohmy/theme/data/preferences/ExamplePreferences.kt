package ru.ohmy.theme.data.preferences

import android.content.Context
import com.noveogroup.preferences.rx.NoveoRxPreferences
import com.noveogroup.preferences.rx.api.RxPreference

@Suppress("unused")
class ExamplePreferences(context: Context) {

    private val factory = NoveoRxPreferences(context)

    val token: RxPreference<String> = factory.getString("token", "unknown")
    val lastUser: RxPreference<String> = factory.getString("last user", "")

}
