package com.noveogroup.template.presentation.splash

import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.presentation.di.DI
import com.noveogroup.template.presentation.di.ScopeInitializer
import com.noveogroup.template.presentation.di.module.ActivityModule
import toothpick.Scope
import toothpick.config.Module

class SplashScopeInitializer : ScopeInitializer({ DI.splashScope }) {

    override fun provideModules(): Array<Module> =
            arrayOf(ActivityModule())

    override fun beforeClose(scope: Scope) {
        scope.getInstance(ScreenInteractor::class.java).stop()
    }
}
