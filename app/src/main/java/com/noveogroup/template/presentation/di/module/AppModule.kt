package com.noveogroup.template.presentation.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.noveogroup.debugdrawer.data.theme.ThemeProxy
import com.noveogroup.template.data.android.lifecycle.ActivityScopePublisher
import com.noveogroup.template.data.android.lifecycle.ForegroundPublisher
import com.noveogroup.template.data.android.system.ResourceManager
import com.noveogroup.template.domain.navigation.Routers
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.domain.navigation.router.MainRouter
import com.noveogroup.template.domain.navigation.router.PaletteRouter
import com.noveogroup.template.presentation.ExampleApplication
import com.noveogroup.template.presentation.common.android.helper.DebugDrawerHelper
import com.noveogroup.template.presentation.di.provider.CalligraphyProvider
import com.noveogroup.template.presentation.di.provider.GsonProvider
import toothpick.config.Module
import uk.co.chrisjenx.calligraphy.CalligraphyConfig


class AppModule(app: ExampleApplication) : Module() {

    private val routers = Routers()
    private val drawerHelper = DebugDrawerHelper(app)

    init {
        //Global
        bind(Context::class.java).toInstance(app)
        bind(Application::class.java).toInstance(app)
        bind(ExampleApplication::class.java).toInstance(app)
        //resource manager
        bind(ResourceManager::class.java).singletonInScope()
        //assets manager

        //Navigation
        bind(GlobalRouter::class.java).toInstance(routers.global)
        bind(MainRouter::class.java).toInstance(routers.main)
        bind(PaletteRouter::class.java).toInstance(routers.palette)

        //Tools
        bind(Gson::class.java).toProvider(GsonProvider::class.java).singletonInScope()
        bind(CalligraphyConfig::class.java).toProvider(CalligraphyProvider::class.java).singletonInScope()


        //Lifecycle
        bind(ActivityScopePublisher::class.java).singletonInScope()
        bind(ForegroundPublisher::class.java).singletonInScope()

        //DebugTools
        bind(DebugDrawerHelper::class.java).toInstance(drawerHelper)
        bind(ThemeProxy::class.java).toInstance(drawerHelper.debugHelper.themeProxy)
    }
}
