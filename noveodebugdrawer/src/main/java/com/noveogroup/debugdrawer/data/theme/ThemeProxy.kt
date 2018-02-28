package com.noveogroup.debugdrawer.data.theme

import com.noveogroup.preferences.api.Preference
import com.noveogroup.preferences.guava.Optional
import com.noveogroup.preferences.lambda.Consumer
import com.noveogroup.preferences.rx.api.RxPreferenceProvider
import com.noveogroup.preferences.rx.api.`RxPreference$`
import io.reactivex.Flowable

class ThemeProxy(private val pref: Preference<Int>) {

    private val rxProvider: RxPreferenceProvider<Int> = `RxPreference$`.rx(pref).provider()

    fun read(): Theme = pref.read().transform { it.asTheme() }.or(Theme.BASE_LIGHT)

    fun change(theme: Theme) {
        pref.save(theme.ordinal)
    }

    fun changes(): Flowable<Theme> = rxProvider.asFlowable().map { optional ->
        optional.transform { it.asTheme() }.or(Theme.BASE_LIGHT)
    }

    fun addListener(listener: Consumer<Optional<Int>>) {
        pref.provider().addListener(listener)
    }

    fun removeListener(listener: Consumer<Optional<Int>>) {
        pref.provider().removeListener(listener)
    }

    private fun Int.asTheme() = Theme.values()[this]

}

