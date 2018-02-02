@file:Suppress("ConstantConditionIf")

package com.noveogroup.template.presentation.di

import com.noveogroup.template.BuildConfig
import com.noveogroup.template.data.android.lifecycle.ActivityScopePublisher
import com.noveogroup.template.presentation.ExampleApplication
import com.noveogroup.template.presentation.di.module.AppModule
import toothpick.Scope
import toothpick.Toothpick
import toothpick.configuration.Configuration
import toothpick.registries.FactoryRegistryLocator
import toothpick.registries.MemberInjectorRegistryLocator
import java.util.concurrent.atomic.AtomicBoolean

fun Scope.inject(any: Any) {
    Toothpick.inject(any, this)
}

fun Scope.close() {
    Toothpick.closeScope(name)
}

object DI {
    private val initialized = AtomicBoolean(false)

    private const val APP_SCOPE = "APP"
    private val SPLASH_SCOPE get() = "SPLASH :: $currentActivity"
    private val MAIN_SCOPE get() = "MAIN :: $currentActivity"

    val appScope: Scope get() = Toothpick.openScope(APP_SCOPE)
    val splashScope: Scope get() = Toothpick.openScopes(APP_SCOPE, SPLASH_SCOPE)
    val mainScope: Scope get() = Toothpick.openScopes(APP_SCOPE, MAIN_SCOPE)

    private var currentActivity: String = ""

    fun initialize(app: ExampleApplication) {
        if (initialized.compareAndSet(false, true)) {
            if (BuildConfig.DEBUG) {
                Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
            } else {
                Toothpick.setConfiguration(Configuration.forProduction().disableReflection())
                FactoryRegistryLocator.setRootRegistry(com.noveogroup.template.FactoryRegistry())
                MemberInjectorRegistryLocator.setRootRegistry(com.noveogroup.template.MemberInjectorRegistry())
            }

            appScope.installModules(AppModule(app))
            appScope.getInstance(ActivityScopePublisher::class.java)
                    .observeScopeLifecycle({ (id, _) -> currentActivity = id })
        }
    }

}
