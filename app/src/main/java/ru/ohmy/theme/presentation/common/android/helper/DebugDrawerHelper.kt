package ru.ohmy.theme.presentation.common.android.helper

import android.app.Activity
import android.app.Application
import com.noveogroup.debugdrawer.DebugHelper
import io.palaima.debugdrawer.DebugDrawer
import io.palaima.debugdrawer.actions.ActionsModule

class DebugDrawerHelper(application: Application) {

    val debugHelper: DebugHelper = DebugHelper(application)

    fun makeDrawer(activity: Activity): DebugDrawer = DebugDrawer.Builder(activity)
            .modules(
                    ActionsModule(debugHelper.createThemeSpinner())
            )
            .build()

}
