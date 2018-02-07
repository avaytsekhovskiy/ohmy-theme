package com.noveogroup.template.presentation

import android.support.multidex.MultiDexApplication
import com.arellomobile.mvp.MvpFacade
import com.noveogroup.template.core.ext.logger
import com.noveogroup.template.data.android.lifecycle.ForegroundPublisher
import com.noveogroup.template.presentation.common.ext.toast
import com.noveogroup.template.presentation.di.DI
import com.noveogroup.template.presentation.di.inject
import net.danlew.android.joda.JodaTimeAndroid
import runErrorHandler
import runRxJavaErrorHandler
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Inject

class ExampleApplication : MultiDexApplication() {

    @Inject
    lateinit var calligraphyConfig: CalligraphyConfig

    @Inject
    lateinit var foregroundPublisher: ForegroundPublisher

    val log by logger()

    override fun onCreate() {
        super.onCreate()

        //Android 4.4 with multidex has no access to some classes before onCreate()
        log.info("created")

        setupErrorHandling()

        /*Dagger*/
        DI.initialize(this)
        DI.appScope.inject(this)

        /*Libs initialization*/
        MvpFacade.init()
        JodaTimeAndroid.init(this)
        CalligraphyConfig.initDefault(calligraphyConfig)

        foregroundPublisher.observeVisibility { toast("$it") }
    }

    private fun setupErrorHandling() {
        runErrorHandler(log) { true }
        runRxJavaErrorHandler(log) { true }
    }

}
