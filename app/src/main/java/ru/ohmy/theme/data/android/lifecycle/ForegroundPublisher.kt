package ru.ohmy.theme.data.android.lifecycle

import android.app.Activity
import android.app.Application
import android.content.Context
import com.jakewharton.rxrelay2.PublishRelay
import ru.ohmy.theme.core.ext.observeSafe
import ru.ohmy.theme.core.ext.trackBy
import ru.ohmy.theme.domain.common.Publisher
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class ForegroundPublisher @Inject constructor(context: Context) : Publisher() {

    private val startedComponentsCounter = AtomicInteger(0)
    private val applicationVisibilityRelay = PublishRelay.create<ApplicationVisibility>()

    private var lastVisibility: ApplicationVisibility? = null
    private var lastTopActivity: Class<out Activity>? = null

    init {
        (context.applicationContext as Application).registerActivityLifecycleCallbacks(SupportLifecycleObserver(
                onActivityStarted = { activity -> notifyOnStart(activity.javaClass) },
                onActivityStopped = { activity -> notifyOnStop(activity.isChangingConfigurations) }))
    }

    fun observeVisibility(action: (ApplicationVisibility) -> Unit) = applicationVisibilityRelay
            .observeSafe(transformer = { it.distinctUntilChanged() }) { action(it) }
            .trackBy(rxHelper)

    private fun notifyOnStart(activeClass: Class<out Activity>) {
        lastTopActivity = activeClass
        if (startedComponentsCounter.getAndIncrement() == 0) {
            post(ApplicationVisibility.FOREGROUND)
        }
    }

    private fun notifyOnStop(changingConfigurations: Boolean) {
        if (startedComponentsCounter.decrementAndGet() == 0 && !changingConfigurations) {
            post(ApplicationVisibility.BACKGROUND)
        }
    }

    private fun post(visibility: ApplicationVisibility) {
        lastVisibility = visibility
        applicationVisibilityRelay.accept(visibility)
    }

    enum class ApplicationVisibility {
        FOREGROUND, BACKGROUND
    }

}
