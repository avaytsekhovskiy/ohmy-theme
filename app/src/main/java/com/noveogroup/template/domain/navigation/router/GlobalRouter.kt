package com.noveogroup.template.domain.navigation.router

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

@Suppress("unused")
class GlobalRouter(cicerone: Cicerone<Router>) : BaseRouter(cicerone) {

    fun startFromSplash() = router.replaceScreen(SPLASH)

    fun startFromMain() = router.replaceScreen(MAIN)

    fun nextMain() = router.navigateTo(MAIN)

    companion object {
        const val SPLASH = "global splash screen"
        const val MAIN = "global main screen"
        const val INSTAGRAM = "instagram application"
    }

}
