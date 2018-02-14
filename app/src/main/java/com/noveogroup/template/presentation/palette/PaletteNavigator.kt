package com.noveogroup.template.presentation.palette


import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.noveogroup.template.domain.navigation.router.PaletteRouter
import com.noveogroup.template.presentation.common.android.BaseActivity
import com.noveogroup.template.presentation.common.navigation.AnimationDescriptor
import com.noveogroup.template.presentation.common.navigation.BaseFragmentNavigator
import com.noveogroup.template.presentation.palette.all.AllFragment
import com.noveogroup.template.presentation.palette.buttons.ButtonsFragment
import ru.terrakok.cicerone.commands.Command

class PaletteNavigator(
        activity: BaseActivity,
        @IdRes containerId: Int,
        fragmentManager: FragmentManager = activity.supportFragmentManager
) : BaseFragmentNavigator(activity, fragmentManager, containerId) {

    @Suppress("unused")
    val empty: Boolean
        get() = findFragmentById<Fragment>(containerId) == null

    override fun setupFragmentTransactionAnimation(
            command: Command,
            currentFragment: Fragment?,
            nextFragment: Fragment,
            fragmentTransaction: FragmentTransaction
    ) {
        val (enter, exit, popEnter, popExit) = when (empty) {
            true -> AnimationDescriptor.FROM_BOTTOM
            else -> AnimationDescriptor.FROM_RIGHT
        }
        fragmentTransaction.setCustomAnimations(enter, exit, popEnter, popExit)
    }

    override fun createFragment(screenKey: String, data: Any?): Fragment = when (screenKey) {
        PaletteRouter.BUTTONS -> ButtonsFragment.newInstance()
        PaletteRouter.ALL_CONTROLS -> AllFragment.newInstance()
        else -> throw Error("Unknown Fragment Key")
    }

    override fun showSystemMessage(message: String) = activity.showToast(message)
}