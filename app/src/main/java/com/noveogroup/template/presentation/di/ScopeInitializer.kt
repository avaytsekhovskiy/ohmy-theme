package com.noveogroup.template.presentation.di

import com.noveogroup.template.core.ext.debugName
import com.noveogroup.template.core.ext.logger
import toothpick.Scope
import toothpick.config.Module
import java.util.concurrent.atomic.AtomicBoolean

abstract class ScopeInitializer(
        scopeProvider: () -> Scope
) {

    private val initialized = AtomicBoolean(false)
    private val scope by lazy(scopeProvider)

    abstract fun provideModules(): Array<Module>

    open fun beforeClose(scope: Scope) {}

    fun install() {
        log.info("install ${scope.name}")
        scope.installModules(*provideModules())
    }

    fun inject(target: Any) {
        if (initialized.compareAndSet(false, true)) {
            log.info("inject from ${scope.name} to ${target.debugName}")
            scope.inject(target)
        }
    }

    fun close() {
        beforeClose(scope)
        log.info("close ${scope.name}")
        scope.close()
    }

    companion object {
        internal val log by logger("ScopeInitializer")
    }
}
