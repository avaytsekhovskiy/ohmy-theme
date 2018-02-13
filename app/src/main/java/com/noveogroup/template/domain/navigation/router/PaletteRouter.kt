package com.noveogroup.template.domain.navigation.router

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class PaletteRouter(cicerone: Cicerone<Router>) : BaseRouter(cicerone) {

    fun displayButtons() = router.replaceScreen(BUTTONS)

    fun displayAllControls() = router.replaceScreen(ALL_CONTROLS)

    companion object {
        const val BUTTONS = "palette buttons"
        const val ALL_CONTROLS = "palette all controls"
    }
}
