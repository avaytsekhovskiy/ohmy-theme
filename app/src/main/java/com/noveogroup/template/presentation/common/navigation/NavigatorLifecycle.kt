package com.noveogroup.template.presentation.common.navigation

import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.presentation.common.android.BaseActivity
import com.noveogroup.template.presentation.common.android.helper.orientation.OrientationHelper

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
