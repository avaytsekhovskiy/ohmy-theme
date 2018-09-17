package ru.ohmy.theme.presentation.di.module

import ru.ohmy.theme.domain.interactor.PaletteInteractor
import ru.ohmy.theme.domain.interactor.state.ScreenInteractor
import toothpick.config.Module

class ActivityModule : Module() {
    init {
        bind(ScreenInteractor::class.java).singletonInScope()
        bind(PaletteInteractor::class.java).singletonInScope()
    }
}
