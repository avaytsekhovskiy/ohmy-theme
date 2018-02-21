package com.noveogroup.template.domain.navigation.router

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class PaletteRouter(cicerone: Cicerone<Router>) : BaseRouter(cicerone) {

    fun displayButtons() = router.replaceScreen(BUTTONS)

    fun displaySelectors() = router.replaceScreen(SELECTORS)

    fun displayAllControls() = router.replaceScreen(ALL_CONTROLS)

    fun displayOverview() = router.replaceScreen(OVERVIEW)

    companion object {
        const val BUTTONS = "palette buttons"
        const val SELECTORS = "palette selectors"
        const val ALL_CONTROLS = "palette all controls"
        const val OVERVIEW = "palette overview"
    }
}
