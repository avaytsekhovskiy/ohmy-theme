package com.noveogroup.template.domain.navigation.router

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class MainRouter(cicerone: Cicerone<Router>) : BaseRouter(cicerone) {

    fun openInheritance() = router.navigateTo(INHERITANCE)

    fun startFromWelcome() = router.newRootScreen(WELCOME)

    fun openThemeOverview() = router.navigateTo(THEME_OVERVIEW)

    fun openSelectorOverview() = router.navigateTo(SELECTOR_OVERVIEW)

    companion object {
        const val WELCOME = "main welcome"
        const val INHERITANCE = "main inheritance"
        const val THEME_OVERVIEW = "main theme overview"
        const val SELECTOR_OVERVIEW = "main selector overview"
    }
}
