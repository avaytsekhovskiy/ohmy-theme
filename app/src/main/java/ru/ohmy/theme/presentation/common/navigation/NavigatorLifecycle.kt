package ru.ohmy.theme.presentation.common.navigation

import ru.ohmy.theme.domain.navigation.router.GlobalRouter
import ru.ohmy.theme.presentation.common.android.BaseActivity
import ru.ohmy.theme.presentation.common.android.helper.orientation.OrientationHelper

class NavigatorLifecycle(
        baseActivity: BaseActivity,
        private val globalRouter: GlobalRouter,
        private val orientationHelper: OrientationHelper
) {

    private val globalNavigator: GlobalNavigator = GlobalNavigator(baseActivity)
    private val navigatorProvider: NavigatorProvider? = baseActivity as? NavigatorProvider

    fun onResume() {
        if (!orientationHelper.rotating) {
            globalRouter.setNavigator(globalNavigator)
            navigatorProvider?.onInstallNavigator()
        }
    }

    fun onPause() {
        globalRouter.removeNavigator()
        navigatorProvider?.onReleaseNavigator()
    }

}
