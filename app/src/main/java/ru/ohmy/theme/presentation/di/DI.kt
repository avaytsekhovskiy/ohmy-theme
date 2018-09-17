@file:Suppress("ConstantConditionIf")

package ru.ohmy.theme.presentation.di

import ru.ohmy.theme.BuildConfig
import ru.ohmy.theme.data.android.lifecycle.ActivityScopePublisher
import ru.ohmy.theme.presentation.ExampleApplication
import ru.ohmy.theme.presentation.di.module.AppModule
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
    private val PALETTE_SCOPE get() = "PALETTE :: $currentActivity"

    val appScope: Scope get() = Toothpick.openScope(APP_SCOPE)
    val splashScope: Scope get() = Toothpick.openScopes(APP_SCOPE, SPLASH_SCOPE)
    val mainScope: Scope get() = Toothpick.openScopes(APP_SCOPE, MAIN_SCOPE)
    val paletteScope: Scope get() = Toothpick.openScopes(APP_SCOPE, PALETTE_SCOPE)

    private var currentActivity: String = ""

    fun initialize(app: ExampleApplication) {
        if (initialized.compareAndSet(false, true)) {
            if (BuildConfig.DEBUG) {
                Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
            } else {
                Toothpick.setConfiguration(Configuration.forProduction().disableReflection())
                FactoryRegistryLocator.setRootRegistry(ru.ohmy.theme.FactoryRegistry())
                MemberInjectorRegistryLocator.setRootRegistry(ru.ohmy.theme.MemberInjectorRegistry())
            }

            appScope.installModules(AppModule(app))
            appScope.getInstance(ActivityScopePublisher::class.java)
                    .observeScopeLifecycle({ (id, _) -> currentActivity = id })
        }
    }

}
