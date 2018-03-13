package com.noveogroup.template.presentation.di

import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.presentation.di.module.ActivityModule
import toothpick.Scope
import toothpick.config.Module

open class ActivityScopeInitializer(scopeProvider: () -> Scope) : ScopeInitializer(scopeProvider) {

    override fun provideModules(): Array<Module> = arrayOf(ActivityModule())

    override fun beforeClose(scope: Scope) {
        scope.getInstance(ScreenInteractor::class.java).stop()
    }
}
