package ru.ohmy.theme.presentation.common.android.helper.orientation

import ru.ohmy.theme.R
import ru.ohmy.theme.core.ext.debugName
import ru.ohmy.theme.presentation.common.android.BaseActivity

@Suppress("MemberVisibilityCanPrivate", "MemberVisibilityCanBePrivate")
class OrientationHelper(
        private val activity: BaseActivity,
        private val phoneMode: ActivityOrientation = ActivityOrientation.PORTRAIT_ONLY,
        private val tabletMode: ActivityOrientation = ActivityOrientation.LANDSCAPE_ONLY
) {

    private lateinit var changeOrientationTask: () -> Unit

    val isPhone: Boolean get() = activity.resources.getBoolean(R.bool.phone)
    val isPortrait: Boolean get() = activity.resources.getBoolean(R.bool.portrait)

    var rotating = false

    fun onCreate() {
        val mode = if (isPhone) phoneMode else tabletMode
        val wrongMode = if (isPortrait) ActivityOrientation.LANDSCAPE_ONLY else ActivityOrientation.PORTRAIT_ONLY

        rotating = mode === wrongMode

        if (rotating) activity.log.info("${activity.debugName} will rotate")

        changeOrientationTask = { activity.requestedOrientation = mode.orientation }
        activity.handler.post(changeOrientationTask)
    }

    fun onPause() {
        activity.handler.removeCallbacks(changeOrientationTask)
    }

}
