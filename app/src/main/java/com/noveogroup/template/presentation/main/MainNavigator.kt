package com.noveogroup.template.presentation.main

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

import com.noveogroup.template.domain.navigation.router.MainRouter
import com.noveogroup.template.presentation.common.android.BaseActivity
import com.noveogroup.template.presentation.common.navigation.AnimationDescriptor
import com.noveogroup.template.presentation.common.navigation.BaseFragmentNavigator
import com.noveogroup.template.presentation.main.page.second.SecondFragment
import com.noveogroup.template.presentation.main.page.welcome.WelcomeFragment

import ru.terrakok.cicerone.commands.Command

class MainNavigator(
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
        val (enter, exit, popEnter, popExit) = when (nextFragment) {
            is WelcomeFragment -> AnimationDescriptor.FROM_BOTTOM
            is SecondFragment -> AnimationDescriptor.FROM_RIGHT
            else -> AnimationDescriptor.FADE
        }
        fragmentTransaction.setCustomAnimations(enter, exit, popEnter, popExit)
    }

    override fun createFragment(screenKey: String, data: Any?) = when (screenKey) {
        MainRouter.WELCOME -> WelcomeFragment.newInstance()
        MainRouter.NEXT -> SecondFragment.newInstance()
        else -> throw Error("Unknown Fragment Key")
    }

    override fun showSystemMessage(message: String) = activity.showToast(message)
}
