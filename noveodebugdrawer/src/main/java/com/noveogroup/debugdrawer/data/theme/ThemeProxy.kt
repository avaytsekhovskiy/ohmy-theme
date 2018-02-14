package com.noveogroup.debugdrawer.data.theme

import com.noveogroup.preferences.api.Preference
import com.noveogroup.preferences.guava.Optional
import com.noveogroup.preferences.lambda.Consumer

class ThemeProxy(private val pref: Preference<Int>) {

    fun read(): Theme = pref.read().transform { Theme.values()[it] }.or(Theme.BASE_LIGHT)

    fun addListener(listener: Consumer<Optional<Int>>) {
        pref.provider().addListener(listener)
    }

    fun removeListener(listener: Consumer<Optional<Int>>) {
        pref.provider().removeListener(listener)
    }

}

