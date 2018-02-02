package com.noveogroup.template.presentation.main

import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.presentation.common.android.BaseActivity
import com.noveogroup.template.presentation.di.DI
import com.noveogroup.template.presentation.di.ScopeInitializer
import com.noveogroup.template.presentation.di.module.ActivityModule
import com.noveogroup.template.presentation.di.module.main.MainModule
import toothpick.Scope


class MainScopeInitializer(
        private val activity: BaseActivity
) : ScopeInitializer({ DI.mainScope }) {

    override fun provideModules() = arrayOf(
            ActivityModule(),
            MainModule(activity.uniqueId())
    )

    override fun beforeClose(scope: Scope) {
        scope.getInstance(ScreenInteractor::class.java).stop()
    }
}
