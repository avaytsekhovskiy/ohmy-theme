package com.noveogroup.template.presentation.di.module

import com.noveogroup.template.domain.interactor.PaletteInteractor
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import toothpick.config.Module

class ActivityModule : Module() {
    init {
        bind(ScreenInteractor::class.java).singletonInScope()
        bind(PaletteInteractor::class.java).singletonInScope()
    }
}
