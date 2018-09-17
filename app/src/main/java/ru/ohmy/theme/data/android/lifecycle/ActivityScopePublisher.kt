package ru.ohmy.theme.data.android.lifecycle

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.jakewharton.rxrelay2.PublishRelay
import ru.ohmy.theme.core.ext.observeSafe
import ru.ohmy.theme.core.ext.trackBy
import ru.ohmy.theme.core.ext.warnOrThrow
import ru.ohmy.theme.domain.common.Publisher
import ru.ohmy.theme.presentation.common.android.BaseActivity
import io.reactivex.Single
import javax.inject.Inject

class ActivityScopePublisher @Inject constructor(context: Context) : Publisher() {

    private val actionRelay = PublishRelay.create<ScopeWrapper>()

    init {
        (context.applicationContext as Application).registerActivityLifecycleCallbacks(SupportLifecycleObserver(
                onActivityCreated = { activity, bundle ->
                    activity.applyIfId(bundle).subscribe(post(ScopeLifecycle.INIT))
                },
                onActivityResumed = { activity ->
                    activity.applyIfId().subscribe(post(ScopeLifecycle.INIT))
                },
                onActivityDestroyed = { activity ->
                    if (activity.isFinishing) {
                        activity.applyIfId().subscribe(post(ScopeLifecycle.RELEASE))
                    }
                }))
    }

    @Suppress("REDUNDANT_ELSE_IN_WHEN")
    fun observeScopeLifecycle(
            init: (ActivityDescriptor) -> Unit = {},
            release: (ActivityDescriptor) -> Unit = {}
    ) = actionRelay
            .observeSafe(consumer = { (scope, descriptor) ->
                when (scope) {
                    ScopeLifecycle.INIT -> init(descriptor)
                    ScopeLifecycle.RELEASE -> release(descriptor)
                    else -> log.warnOrThrow("Unknown Scope Lifecycle state")
                }
            })
            .trackBy(rxHelper)

    private fun post(phase: ScopeLifecycle): (ActivityDescriptor) -> Unit =
            { descriptor -> actionRelay.accept(ScopeWrapper(phase, descriptor)) }

    private fun Activity.applyIfId(savedInstanceState: Bundle? = null) = Single.defer<ActivityDescriptor> {
        return@defer when {
            this is BaseActivity -> Single.just(ActivityDescriptor(uniqueId(savedInstanceState), this))
            else -> Single.never()
        }
    }

    enum class ScopeLifecycle {
        INIT, RELEASE
    }

    private data class ScopeWrapper(
            val scopeLifecycle: ScopeLifecycle,
            val activityDescriptor: ActivityDescriptor
    )

    data class ActivityDescriptor(
            val id: String,
            val activity: BaseActivity
    )
}
