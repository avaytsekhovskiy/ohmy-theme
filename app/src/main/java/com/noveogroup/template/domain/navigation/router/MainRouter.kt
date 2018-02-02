package com.noveogroup.template.domain.navigation.router

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class MainRouter(cicerone: Cicerone<Router>) : BaseRouter(cicerone) {

    fun openSecond() = router.navigateTo(NEXT)

    fun startFromWelcome() = router.newRootScreen(WELCOME)

    companion object {
        const val WELCOME = "main welcome"
        const val NEXT = "main next"
    }
}
