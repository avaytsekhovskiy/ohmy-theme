package ru.ohmy.theme.presentation.common.android.helper.orientation

import android.content.pm.ActivityInfo

enum class ActivityOrientation(val orientation: Int) {
    PORTRAIT_ONLY(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT),
    LANDSCAPE_ONLY(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE),
    BOTH(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR)
}
