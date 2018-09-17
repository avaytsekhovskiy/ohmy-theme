package ru.ohmy.theme.domain.navigation

import ru.ohmy.theme.domain.navigation.router.GlobalRouter
import ru.ohmy.theme.domain.navigation.router.MainRouter
import ru.ohmy.theme.domain.navigation.router.PaletteRouter

import ru.terrakok.cicerone.Cicerone

class Routers {

    val global: GlobalRouter by lazy { GlobalRouter(Cicerone.create()) }

    val main: MainRouter by lazy { MainRouter(Cicerone.create()) }

    val palette: PaletteRouter by lazy { PaletteRouter(Cicerone.create()) }

}
