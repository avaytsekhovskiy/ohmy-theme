package ru.ohmy.theme.presentation.di

import ru.ohmy.theme.domain.interactor.state.ScreenInteractor
import ru.ohmy.theme.presentation.di.module.ActivityModule
import toothpick.Scope
import toothpick.config.Module

open class ActivityScopeInitializer(scopeProvider: () -> Scope) : ScopeInitializer(scopeProvider) {

    override fun provideModules(): Array<Module> = arrayOf(ActivityModule())

    override fun beforeClose(scope: Scope) {
        scope.getInstance(ScreenInteractor::class.java).stop()
    }
}
