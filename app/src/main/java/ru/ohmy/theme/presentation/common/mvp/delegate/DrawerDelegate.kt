package ru.ohmy.theme.presentation.common.mvp.delegate

import android.app.Activity
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import ru.ohmy.theme.core.ext.logger
import ru.ohmy.theme.presentation.common.mvp.view.ui.DrawerView

class DrawerDelegate(
        private val orientation: Orientation,
        private var fragmentView: View,
        private var drawerLayout: DrawerLayout
) : DrawerView {

    val log by logger()

    val isDrawerOpened: Boolean
        get() = drawerLayout.isDrawerOpen(orientation.gravity)

    override fun open() {
        log.debug("opened")
        drawerLayout.openDrawer(fragmentView)
    }

    override fun close() {
        log.debug("close")
        drawerLayout.closeDrawer(fragmentView)
    }

    override fun lockOpened() {
        log.debug("lock")
        disableDrawerSwipe(false)
    }

    override fun lockClosed() {
        log.debug("lock")
        disableDrawerSwipe(true)
    }

    override fun lockAsIs() {
        log.debug("lock")
        disableDrawerSwipe()
    }

    override fun unlock() {
        log.debug("unlock")
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    fun removeListener(listener: DrawerLayout.DrawerListener) = drawerLayout.removeDrawerListener(listener)

    fun addListener(listener: DrawerLayout.DrawerListener) = drawerLayout.addDrawerListener(listener)

    fun createToggle(activity: Activity, toolbar: Toolbar) =
            ActionBarDrawerToggle(activity, drawerLayout, toolbar, 0, 0)

    private fun disableDrawerSwipe(close: Boolean? = null) {
        drawerLayout.setDrawerLockMode(when {
            close == true -> DrawerLayout.LOCK_MODE_LOCKED_CLOSED

            close == false -> DrawerLayout.LOCK_MODE_LOCKED_OPEN

            drawerLayout.isDrawerOpen(fragmentView) -> DrawerLayout.LOCK_MODE_LOCKED_OPEN

            else -> DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        })
    }

    enum class Orientation(internal val gravity: Int = 0) {
        LEFT(Gravity.LEFT),
        RIGHT(Gravity.RIGHT)
    }

    companion object {
        fun composeListener(onSlide: (Float) -> Unit = {},
                            onOpen: () -> Unit = {},
                            onClose: () -> Unit = {},
                            onStateChanged: (Int) -> Unit = {}
        ) = object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) = onSlide(slideOffset)

            override fun onDrawerOpened(drawerView: View) = onOpen()

            override fun onDrawerClosed(drawerView: View) = onClose()

            override fun onDrawerStateChanged(newState: Int) = onStateChanged(newState)
        }
    }

}
