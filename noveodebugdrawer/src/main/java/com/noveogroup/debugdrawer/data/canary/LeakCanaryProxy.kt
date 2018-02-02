package com.noveogroup.debugdrawer.data.canary

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

class LeakCanaryProxy(private var watcher: RefWatcher? = null) {

    fun initialize(app: Application) {
        app.takeIf(LeakCanary::isInAnalyzerProcess)?.let { watcher = LeakCanary.install(it) }
    }

    fun watch(target: Any) = watcher?.watch(target)

}
