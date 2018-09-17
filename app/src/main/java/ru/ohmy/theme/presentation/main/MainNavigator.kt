package ru.ohmy.theme.presentation.main

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import ru.ohmy.theme.domain.navigation.router.MainRouter
import ru.ohmy.theme.presentation.common.android.BaseActivity
import ru.ohmy.theme.presentation.common.navigation.AnimationDescriptor
import ru.ohmy.theme.presentation.common.navigation.BaseFragmentNavigator
import ru.ohmy.theme.presentation.main.page.inheritance.InheritanceFragment
import ru.ohmy.theme.presentation.main.page.selector_overview.SelectorOverviewFragment
import ru.ohmy.theme.presentation.main.page.theme_overview.ThemeOverviewFragment
import ru.ohmy.theme.presentation.main.page.welcome.WelcomeFragment
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
            else -> AnimationDescriptor.FROM_RIGHT
        }
        fragmentTransaction.setCustomAnimations(enter, exit, popEnter, popExit)
    }

    override fun createFragment(screenKey: String, data: Any?) = when (screenKey) {
        MainRouter.WELCOME -> WelcomeFragment.newInstance()
        MainRouter.INHERITANCE -> InheritanceFragment.newInstance()
        MainRouter.THEME_OVERVIEW -> ThemeOverviewFragment.newInstance()
        MainRouter.SELECTOR_OVERVIEW -> SelectorOverviewFragment.newInstance()
        else -> throw Error("Unknown Fragment Key")
    }

    override fun showSystemMessage(message: String) = activity.showToast(message)

}
