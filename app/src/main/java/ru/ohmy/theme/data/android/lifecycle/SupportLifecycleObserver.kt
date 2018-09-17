package ru.ohmy.theme.data.android.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle

class SupportLifecycleObserver(
        private val onActivityCreated: ((Activity, Bundle?) -> Unit)? = null,
        private val onActivityStarted: ((Activity) -> Unit)? = null,
        private val onActivityResumed: ((Activity) -> Unit)? = null,
        private val onActivityPaused: ((Activity) -> Unit)? = null,
        private val onActivityStopped: ((Activity) -> Unit)? = null,
        private val onActivitySaveInstanceState: ((Activity, Bundle?) -> Unit)? = null,
        private val onActivityDestroyed: ((Activity) -> Unit)? = null
) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        onActivityCreated?.invoke(activity, bundle)
    }

    override fun onActivityStarted(activity: Activity) {
        onActivityStarted?.invoke(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        onActivityResumed?.invoke(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        onActivityPaused?.invoke(activity)
    }

    override fun onActivityStopped(activity: Activity) {
        onActivityStopped?.invoke(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle?) {
        onActivitySaveInstanceState?.invoke(activity, bundle)
    }

    override fun onActivityDestroyed(activity: Activity) {
        onActivityDestroyed?.invoke(activity)
    }
}
