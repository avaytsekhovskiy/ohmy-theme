package com.noveogroup.template.domain.navigation.router

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class MainRouter(cicerone: Cicerone<Router>) : BaseRouter(cicerone) {

    fun openInheritance() = router.navigateTo(INHERITANCE)

    fun startFromWelcome() = router.newRootScreen(WELCOME)

    companion object {
        const val WELCOME = "main welcome"
        const val INHERITANCE = "main inheritance"
    }
}
