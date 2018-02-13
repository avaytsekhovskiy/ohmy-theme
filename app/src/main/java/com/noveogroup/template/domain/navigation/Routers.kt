package com.noveogroup.template.domain.navigation

import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.domain.navigation.router.MainRouter
import com.noveogroup.template.domain.navigation.router.PaletteRouter

import ru.terrakok.cicerone.Cicerone

class Routers {

    val global: GlobalRouter by lazy { GlobalRouter(Cicerone.create()) }

    val main: MainRouter by lazy { MainRouter(Cicerone.create()) }

    val palette: PaletteRouter by lazy { PaletteRouter(Cicerone.create()) }

}
