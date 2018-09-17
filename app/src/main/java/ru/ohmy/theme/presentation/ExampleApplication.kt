package ru.ohmy.theme.presentation

import android.support.multidex.MultiDexApplication
import com.arellomobile.mvp.MvpFacade
import ru.ohmy.theme.core.ext.logger
import ru.ohmy.theme.data.android.lifecycle.ForegroundPublisher
import ru.ohmy.theme.presentation.common.ext.toast
import ru.ohmy.theme.presentation.di.DI
import ru.ohmy.theme.presentation.di.inject
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

        /*DI*/
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
