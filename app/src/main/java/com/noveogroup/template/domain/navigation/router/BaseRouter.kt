@file:Suppress("MemberVisibilityCanPrivate")

package com.noveogroup.template.domain.navigation.router

import com.noveogroup.template.BuildConfig
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router

@Suppress("unused")
open class BaseRouter(private val cicerone: Cicerone<Router>) {

    @Suppress("MemberVisibilityCanBePrivate")
    var cache: PagesCache? = null

    val router: Router
        get() = cicerone.router

    fun exit(message: String? = null) = when (message) {
        null -> router.exit()
        else -> router.exitWithMessage(message)
    }

    fun exitWithResult(code: Int, result: Any) = router.exitWithResult(code, result)

    fun defaultExit() = exit("default exit".takeIf { BuildConfig.DEBUG })

    fun setNavigator(navigator: Navigator) = cicerone.navigatorHolder.setNavigator(navigator)

    fun removeNavigator() = cicerone.navigatorHolder.removeNavigator()

    //Access from different threads (from interactors)
    fun clearCache() = synchronized(this) {
        cache?.clearCache().also { cache = null }
    }

    /**
     * Get stored cache if one was stored. Create with creator function otherwise.
     *
     * @param cacheCreator fabric method for cache creation.
     * @param <T>          MainRouterCache implementation.
     * @return previous or newly created cache object.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : PagesCache> getOrCreateCache(cacheCreator: (() -> T)? = null): T? = synchronized(this) {
        return@synchronized cacheCreator
                ?.takeIf { cache == null }
                ?.invoke()
                ?.also { cache = it }
                ?: cache as T
    }

    interface PagesCache {
        fun clearCache()
    }
}

