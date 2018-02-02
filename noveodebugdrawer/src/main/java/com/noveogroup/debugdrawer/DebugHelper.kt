package com.noveogroup.debugdrawer

import android.app.Application

import com.facebook.stetho.Stetho
import com.noveogroup.debugdrawer.data.canary.LeakCanaryProxy
import com.noveogroup.debugdrawer.data.endpoint.EndpointProxy

import org.slf4j.LoggerFactory

class DebugHelper(app: Application) {

    val configuration: DebugBuildConfiguration =
            DebugBuildConfiguration.init(app).apply { enableDebug() }

    val leakCanaryProxy: LeakCanaryProxy = LeakCanaryProxy()
    val endpointProxy: EndpointProxy

    init {
        prepareEnablerModule(app, configuration)
        endpointProxy = prepareSelectorModule(configuration)
    }

    private fun prepareEnablerModule(app: Application, config: DebugBuildConfiguration) {
        val stethoEnabler = Enabler.create(Config.ENABLER_STETHO) { enabled ->
            app.also { LOGGER.info("init stetho $enabled") }.takeIf { enabled }?.let(Stetho::initializeWithDefaults)
        }

        val leakEnabler = Enabler.create(Config.ENABLER_LEAK) { enabled ->
            app.also { LOGGER.info("init leak canary {}", enabled) }.takeIf { enabled }?.let(leakCanaryProxy::initialize)
        }

        EnablerModule.init(config, stethoEnabler, leakEnabler)
    }

    private fun prepareSelectorModule(config: DebugBuildConfiguration): EndpointProxy {
        val endpointSelector = Selector(END_KEY, *ENDPOINTS).apply { releaseValue = ENDPOINTS[0] }
        SelectorModule.init(config, endpointSelector)

        return EndpointProxy(config.selectorProvider)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(DebugHelper::class.java)
        private val END_KEY = Config.SELECTOR_ENDPOINT
        private val ENDPOINTS = Config.ENDPOINTS
    }
}
