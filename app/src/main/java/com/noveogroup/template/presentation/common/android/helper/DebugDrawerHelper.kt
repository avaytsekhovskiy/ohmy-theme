package com.noveogroup.template.presentation.common.android.helper

import android.app.Activity
import android.app.Application
import com.noveogroup.debugdrawer.DebugHelper
import com.noveogroup.debugdrawer.EnablerModule
import com.noveogroup.debugdrawer.GradleModule
import com.noveogroup.debugdrawer.SelectorModule
import com.noveogroup.template.BuildConfig
import io.palaima.debugdrawer.DebugDrawer
import io.palaima.debugdrawer.commons.BuildModule
import io.palaima.debugdrawer.commons.DeviceModule
import io.palaima.debugdrawer.commons.SettingsModule
import io.palaima.debugdrawer.scalpel.ScalpelModule

class DebugDrawerHelper(application: Application) {

    val debugHelper: DebugHelper = DebugHelper(application)

    fun makeDrawer(activity: Activity): DebugDrawer = DebugDrawer.Builder(activity)
            .modules(GradleModule(BuildConfig::class.java),
                    SelectorModule(debugHelper.configuration),
                    BuildModule(activity),
                    DeviceModule(activity),
                    ScalpelModule(activity),
                    EnablerModule(debugHelper.configuration),
//                    NetworkModule(activity),          //requires permissions
//                    OkHttp3Module(),                  //get OkHttpClient from Injector
//                    GlideModule(Glide.get(activity)), //incompatible with glide 4.+
                    SettingsModule(activity)
            )
            .build()

}
