package ru.ohmy.theme.domain.navigation.router

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class PaletteRouter(cicerone: Cicerone<Router>) : BaseRouter(cicerone) {

    fun displayButtons() = router.replaceScreen(BUTTONS)

    fun displaySelectors() = router.replaceScreen(SELECTORS)

    fun displayPickers() = router.replaceScreen(PICKERS)

    fun displayAllControls() = router.replaceScreen(TEXTS)

    companion object {
        const val BUTTONS = "palette buttons"
        const val SELECTORS = "palette selectors"
        const val PICKERS = "palette pickers"
        const val TEXTS = "palette texts"
    }
}
